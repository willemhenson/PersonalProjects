<!DOCTYPE html>
<?php include 'head.txt';?>
<body>
<?php include 'header.txt';?>
  <main>
    <div class="container">
      <h2>Frequently Asked Questions</h2>
      <h3>General FAQs</h3>
    </div>
		<div class="container">
      <p style="margin-bottom: 36pt">Everything you need to know about eatyourway and how it works.
        Can't find an answer? <a href="contact.php"><u>Please ask our firendly team</u></a>.</p>
      <hr>
    </div>
    <div class="container">
      <button class="accordion">How can I contact the eatyourway team, ask questions or make suggestions?</button>
      <div class="panel">
        <p>You can ask any questions or make suggestions by emailing us at <a href="mailto:contact.eatyourway@gmail.com"><u>contact.eatyourway@gmail.com</u></a>.</p>
        <p>More information is available on the <a href="contact.php"><u>Contact Us</u></a> page.</p>
      </div>
      <hr>
    </div>
    <div class="container">
      <button class="accordion">Is the information and data on products verified?</button>
      <div class="panel">
        <p>We get all of our data from the Open Food Facts API. There is no guarantee that the information provided is correct and some information may be missing for certain products.</p>
        <p>To learn more about the Open Food Facts API, please visit the <a href="https://world.openfoodfacts.org/" target="_blank"><u>Open Food Facts</u></a> website.</p>
        <p> You can learn more about the data we use from the <a href="privacy policy.php"><u>Our Data</u></a> page.</p>
      </div>
      <hr>
    </div>
    <div class="container">
      <button class="accordion">Can I request that a product be added to the website?</button>
      <div class="panel">
        <p>At the moment we get all of our data from the Open Food Facts API. This means that we cannot add additional products to the website. However, you may be able to add a prodct to the Open Food Facts database which would add the product to our website.</p>
        <p>To learn more about the Open Food Facts API, please visit the <a href="https://world.openfoodfacts.org/" target="_blank"><u>Open Food Facts</u></a> website.</p>
        <p> You can learn more about the data we use from the <a href="privacy policy.php"><u>Our Data</u></a> page.</p>
      </div>
      <hr>
    </div>
    <div class="container">
      <button class="accordion">Can I save my favorite products and view their nutritional and environmental information later?</button>
      <div class="panel">
        <p>There is currently no way to save products to view their information at a later time. In order to view the nutritional and environmental information about a proudct, you will need to scan the barcode of the product using our <a href="scan(search).php"><u>barcode scanner</u></a></p>
      </div>
      <hr>
    </div>
    <div class="container">
      <button class="accordion">Do I need to create an account?</button>
      <div class="panel">
        <p>No, you do not need to create an account to be able to use <i>eatyourway</i>.</p>
        <p>By creating an account you can choose your own preferences and check if they are met by the products you scan, allowing for a seamless expreience.</p>
        <p>But don't worry, if you choose not to create an account you can still make use of <i>eatyourway</i> to provide you with accessible nutritional information. When you use <i>eatyourway</i> as a guest, rather than a personalised response checking if a product matches your preferences, you will be provided with a summary of the scanned product. This summary will include allergens, ingredients, vegetarian and vegan status and much more!</p>
      </div>
      <hr>
		</div>
    <script type="text/javascript">
      
      var acc = document.getElementsByClassName("accordion");
      var i;

      for (i = 0; i < acc.length; i++) {
      acc[i].addEventListener("click", function() {
        this.classList.toggle("active");
        var panel = this.nextElementSibling;
        if (panel.style.display === "block") panel.style.display = "none";
        else panel.style.display = "block";
      }

    </script>
  </main>
<?php include 'footer.txt';?>
</body>
</html>
