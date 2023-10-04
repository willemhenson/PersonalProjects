async function requestApi(barcode) {
    // This function takes a barcode string and returns a promise of the json result from the API
    let json = fetch('https://world.openfoodfacts.org/api/v0/product/' + barcode + '.json')
        .then(response => {
            return response.json();
        })
    return json
}

function tryExtract(data) {
    // This function tries to get data with key from json
    if (data != null) return data;
    else return null;
}

function isGoodCall(api_data) {
    if (api_data["status"] == 1) return true;
    else return false;
}

function extractApiJSON(data) {
    // This function takes the json data of a product and extracts useful info, returns dictionary of data
    // let product_data = {"brands":                   [string],
    //                     "ingredients":              string,
    //                     "has plastic":             boolean,
    //                     "has palm":                 boolean,
    //                     "is vegetarian":            boolean,
    //                     "is vegan":                 boolean,
    //                     "nutrigrade":               string,
    //                     "ecograde":                 string,
    //                     "nova":                     int,
    //                     "co2/g":                    float};

    let product_data = {"name":                     null,
                        "barcode":                  null,
                        "categories":               null,
                        "image url":                null,
                        "brands":                   null,
                        "ingredients":              null,
                        "has plastic":             null,
                        "has palm":                 null,
                        "is vegetarian":            null,
                        "is vegan":                 null,
                        "nutrigrade":               null,
                        "ecograde":                 null,
                        "nova":                     null,
                        "co2/g":                    null,
                        "nutrition":                null,
                        "quantity":                 null};


    product_data["name"] = tryExtract(data["product_name"]);

    product_data["brands"] = tryExtract(data["brands"]);

    product_data["image url"] = tryExtract(data["image_url"]);


    product_data["barcode"] = tryExtract(data["code"]);

    product_data["nutrigrade"] = tryExtract(data["nutriscore_grade"]);

    product_data["nova"] = tryExtract(data["nova_group"]);
    if (product_data["nova"] != null) product_data["nova"] = parseFloat(product_data["nova"]);

    let ingredients = tryExtract(data["ingredients_text"]);
    if (ingredients != null) product_data["ingredients"] = ingredients;
    let allergens = tryExtract(data["allergens_tags"]);
    if (allergens != null) product_data["ingredients"] += allergens.toString().replace(/en:/g, "");
    let traces = tryExtract(data["traces"]);
    if (traces != null) product_data["ingredients"] += traces.toString().replace(/en:/g, "");
    let additives = tryExtract(data["additives_tags"]);
    if (additives != null) product_data["ingredients"] += additives.toString().replace(/en:/g, "");
    if(product_data["ingredients"] != null) product_data["ingredients"] = product_data["ingredients"].toLowerCase();

    let vvp_data = tryExtract(data["ingredients_analysis_tags"]);
    if (vvp_data != null) {
        if (vvp_data[0] == "en:palm-oil-free") product_data["has palm"] = false;
        else if (vvp_data[0] != "en:palm-oil-content-unknown") product_data["has palm"] = true;

        if (vvp_data[1] == "en:vegan") product_data["is vegan"] = true;
        else if (vvp_data[1] != "en:vegan-status-unknown") product_data["is vegan"] = false;

        if (vvp_data[2] == "en:vegetarian") product_data["is vegetarian"] = true;
        else if (vvp_data[2] != "en:vegetarian-status-unknown") product_data["is vegetarian"] = false;
    }

    product_data["co2/g"] = tryExtract(data["ecoscore_data"]["agribalyse"]["co2_total"]);

    product_data["ecograde"] = tryExtract(data["ecoscore_grade"]);

    let quantity = tryExtract(data["quantity"]);
    if (quantity != null) product_data["quantity"] = parseFloat(quantity);

    let no_plastics = tryExtract(data["ecoscore_data"]["adjustments"]["packaging"]["non_recyclable_and_non_biodegradable_materials"]);
    if (no_plastics != null) {
        if (no_plastics != 0) product_data["has plastic"] = true;
        else product_data["has plastic"] = false;
    }

    //not checked against pref data
    product_data["categories"] = tryExtract(data["categories"]);
    let nutrition = tryExtract(data["nutrient_levels_tags"]);
    if (nutrition != null) {
        for (i=0;i<nutrition.length;i++) {
            nutrition[i] = nutrition[i].replace("en:", "");
        }
        product_data["nutrition"] = nutrition;
    }


    return product_data;
}

function testPreferences(product_data) {
    //function testPreferences(product_data,user_data) {
    // takes the 2 js dictionaries and constructs a new dict that checks if each field acceptable
    
    //(seed)
    let user_data = getPreferences();


    let satisfied = {"brands":                  null,
                    "ingredients":              null,
                    "plastic":                 null,
                    "palm":                     null,
                    "vegetarian":               null,
                    "vegan":                    null,
                    "nutrigrade":               null,
                    "ecograde":                 null,
                    "nova":                     null,
                    "co2/g":                    null,
                    "message":            "WARNING:",
                    "status":                   null};

    //check brands
    let brands = user_data["exclude brands"];
    if (product_data["brands"] != null) {
        for (i=0;i<brands.length;i++) {
            if (product_data["brands"].toLowerCase().includes(brands[i].toLowerCase()) && brands[i]!="") {
                satisfied["brands"] = false;
                satisfied["message"] += " affiliated with:'" + brands[i] +"',";
            }
        }
        if (satisfied["brands"] == null) satisfied["brands"] = true;
    }

    //check ingredients
    let ingredients = user_data["exclude ingredients"];
    if (product_data["ingredients"] != null) {
        for (i=0;i<ingredients.length;i++) {
            if (product_data["ingredients"].toLowerCase().includes(ingredients[i].toLowerCase()) && ingredients[i]!="") {
                satisfied["ingredients"] = false;
                satisfied["message"] += " contains:'" + ingredients[i] +"',"; 
            }
        }
        if (satisfied["ingredients"] == null) satisfied["ingredients"] = true;
    }

    //check plastics
    if (user_data["exclude plastic"] == false) satisfied["plastic"] = true;
    else if (user_data["exclude plastic"] == true) {
        if (product_data["has plastic"] == true) {
            satisfied["plastic"] = false;
            satisfied["message"] += " contains single-use plastics,";
        }
        else if (product_data["has plastic"] == false) satisfied["plastic"] = true;
    }

    //check palm
    if (user_data["exclude palm"] == false) satisfied["palm"] = true;
    else if (user_data["exclude palm"] == true) {
        if (product_data["has palm"] == true) {
            satisfied["palm"] = false;
            satisfied["message"] += " contains palm oil,"; 
        }
        else if (product_data["has palm"] == false) satisfied["palm"] = true;
    }

    //check vegetarian
    if (user_data["only vegetarian"] == false) satisfied["vegetarian"] = true;
    else if (user_data["only vegetarian"] == true) {
        if (product_data["is vegetarian"] == true) {
            satisfied["vegetarian"] = false;
            satisfied["message"] += " is not vegetarian,"; 
        }
        else if (product_data["is vegetarian"] == false) satisfied["vegetarian"] = true;
    }

    //check vegan
    if (user_data["only vegan"] == false) satisfied["vegan"] = true;
    else if (user_data["only vegan"] == true) {
        if (product_data["is vegan"] == true) {
            satisfied["vegan"] = false;
            satisfied["message"] += " is not vegan,";
        }
        else if (product_data["is vegan"] == false) satisfied["vegan"] = true;
    }

    //check nutrigrade
    if (user_data["min nutrigrade"].toLowerCase() == "e") satisfied["nutrigrade"] = true;
    else {
        let user_min_grade = "abcde".indexOf(user_data["min nutrigrade"].toLowerCase());
        if (product_data["nutrigrade"] != null) {
            let product_grade = "abcde".indexOf(product_data["nutrigrade"].toLowerCase());
            if (user_min_grade >= product_grade) satisfied["nutrigrade"] = true;
            else {
                satisfied["nutrigrade"] = false;
                satisfied["message"] += " is too low nutrigrade,";
            }
        }
    }

    //check ecograde
    if (user_data["min ecograde"].toLowerCase() == "e") satisfied["ecograde"] = true;
    else {
        let user_min_grade1 = "abcde".indexOf(user_data["min ecograde"].toLowerCase());
        if (product_data["ecograde"] != null) {
            let product_grade1 = "abcde".indexOf(product_data["ecograde"].toLowerCase());
            if (user_min_grade1 >= product_grade1) satisfied["ecograde"] = true;
            else {
                satisfied["ecograde"] = false;
                satisfied["message"] += " is too low ecograde,";
            }
        }
    }

    //check nova
    if (user_data["max nova"] == 4) satisfied["nova"] = true;
    else {
        if (product_data["nova"] != null) {
            if (user_data["max nova"] >= product_data["nova"]) satisfied["nova"] = true;
            else {
                satisfied["nova"] = false;
                satisfied["message"] += " is too high in nova classification,";
            }
        }
    }

    //check co2
    if (product_data["co2/g"] != null) {
        if (user_data["max co2/g"] >= product_data["co2/g"]) satisfied["co2/g"] = true;
        else {
            satisfied["co2/g"] = false;
            satisfied["message"] += " is too high in co2/g,";
        }
    }

    satisfied = nullToUnknown(satisfied);

    for (const [key, value] of Object.entries(satisfied)) {
        if (value == "unknown") {
            console.log("missing data x1");
            satisfied["status"] = "unknown";
            if (key != "status") satisfied["message"] += " unknown:" + key + ",";
        }
    }

    if (satisfied["message"] == "WARNING:" && satisfied["status"] != "unknown") {
        satisfied["message"] = "all preferences satisfied";
        console.log(satisfied["message"]);
        satisfied["status"] = "yes";
    }
    else if (satisfied["status"] != "unknown") {
        console.log(satisfied["message"]);
        satisfied["status"] = "no";
    }
    return satisfied;

}














/*function objectToTable(product){
    // This function takes a product element and returns a html string for the table
    // It also does some other stuff which I'm not going to mess with

    // for (var i=0;i<ids.length;i++){
    // 	console.log(ids[i])
    // 	if (ids[i] != "image")
    // 		document.getElementById(ids[i]).innerHTML = product[ids[i]]
    // 	else
    // 		document.getElementById("image").innerHTML = `<img src=${product["image"]}>`;
    // }
    my_str = '<table><tr><th>name</th><th>value</th></tr>'
    for (const [key, value] of Object.entries(product)) {
        if (key != "image")
            my_str += '<tr><td>' + key + '</td><td>' + value + '</td></tr>'
        else
            my_str += '<tr><td>' + key + '</td><td><img src=' + value + '></td></tr>'
    }
    my_str += '</table.'
    //document.getElementById("results-data").innerHTML = my_str;

    stored = JSON.parse(localStorage._stored)
    my_str = '<table><tr><th>filter</th><th>setting</th></tr>'
    for (const [key, value] of Object.entries(stored)) {
        my_str += '<tr><td>' + key + '</td><td>' + value + '</td></tr>'
    }
    my_str += '</table>'
    //document.getElementById("preferences").innerHTML = my_str;

    to_check_ids = ["vegan", "palm", "veg"]
    flag = true;
    var incomp_filter = "";
    for (var i = 0; i < to_check_ids.length; i++) {
        id = to_check_ids[i]
        if (stored[id] == false) {
            console.log(id + " choice satisfied")
        }
        else if (product[id] == true) {
            console.log(id + " choice satisfied")
        }
        else {
            console.log(id + " choice NOT satisfied")
            flag = false;
            incomp_filter = "vegan/palm/veg"
            
        }
    }
    if (stored["plastic"] == true) {
        if (product["non_bios"] > 0) {
            console.log("warning, has none bio materials")
            flag = false
            incomp_filter = "non bio packaging"
        }
        else
            console.log("all bio materials")
    }
    else
        console.log("all bio materials")
    if (stored["brands"].includes(product["brands"])) {
        console.log("brands choice NOT satisfied")
        flag = false;
        incomp_filter = "brands"
    }
    else {
        console.log("brands choice satisfied")
    }
    ingredients = product["ingred"].split(",")
    incomp_ingred = false;
    for (var i = 0; i < ingredients.length; i++) {
        if (stored["ingred"].includes(ingredients[i])) {
            console.log("found incompatible ingred: " + ingredients[i])
            var ingred = ingredients[i];
            flag = false;
            incomp_ingred = true;
            incomp_filter = "ingred"
        }
    }
    if (incomp_ingred == false)
        console.log("no incompatible ingredients")
    let barcode_get_str = "&barcode=" + product["code"];
    if (flag) {
        document.getElementById("result-link").href = "results.php?result=y" + barcode_get_str;
    }
    else {
        if (incomp_ingred){
            document.getElementById("result-link").href = "results.php?result=n&incomp="+incomp_filter+"&ingred="+ingred + barcode_get_str;
        }
        else {
            document.getElementById("result-link").href = "results.php?result=n&incomp="+incomp_filter + barcode_get_str;
        }
    }
    return my_str
}*/

function processData(data){
    // This takes a json data file and returns a html table
    if (isLoggedIn()) {
        console.log("Logged in");
        if (isGoodCall(data)) {
            let product_data = extractApiJSON(data["product"]);
            let satisfied = testPreferences(product_data);
            console.log(product_data);
            console.log(satisfied);
            localStorage.setItem("product data",JSON.stringify(product_data));
            localStorage.setItem("satisfied",JSON.stringify(satisfied));
            window.location.href = "./results.php";
        }
        else {
            console.log("no product found");
            document.getElementById("result-error").innerHTML = "sorry, no product found!";
        }
    }
    else {
        console.log("Not logged in");
        if (isGoodCall(data)) {
            window.location.href = "info.php?barcode=" + data["code"];
        }
        else {
            console.log("no product found");
            document.getElementById("result-error").innerHTML = "sorry, no product found!";
        }

    }
}

function processBarcode(barcode) {
    requestApi(barcode).then(api_data => processData(api_data));
}

function redundantInfo(){
    product["packaging"] = data["product"]["packaging"];
    product["ecoscore"] = data["product"]["ecoscore_score"];
    product["packscore"] = data["product"]["ecoscore_data"]["adjustments"]["packaging"]["score"];
    product["nutrition"] = data["product"]["nutrient_levels_tags"]
    product["manplace"] = data["product"]["manufacturing_places"]
    product_data["stores"] = data["product"]["stores"]
}