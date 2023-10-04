// Much of this code is copied and pasted so we may need to credit zxing-js
function startScanning(codeReader, selectedDeviceId) {
    codeReader.decodeFromVideoDevice(selectedDeviceId, 'video', (result, err) => {
        if (result) { //If barcode has been read
            console.log("barcode scanned:"+result);
            processBarcode(result);
        }
        if (err && !(err instanceof ZXing.NotFoundException)) {
            console.error(err);
            scanError(err);
        }
    })
    console.log(`Started continous decode from camera with id ${selectedDeviceId}`);
}
window.addEventListener('load', function () { // Runs when the entire page is loaded
    let selectedDeviceId;
    const codeReader = new ZXing.BrowserMultiFormatReader()
    console.log('ZXing code reader initialized')
    codeReader.listVideoInputDevices()
        .then((videoInputDevices) => {
            // Once the input devices have been recognised (i.e given permission to use camera)

            // Create sourceSelect
            const sourceSelect = document.getElementById('sourceSelect')
            selectedDeviceId = videoInputDevices[0].deviceId
            if (videoInputDevices.length >= 1) {
                videoInputDevices.forEach((element) => {
                    const sourceOption = document.createElement('option')
                    sourceOption.text = element.label
                    sourceOption.value = element.deviceId
                    sourceSelect.appendChild(sourceOption)
                })

                sourceSelect.onchange = () => {
                    console.log("source changed")
                    selectedDeviceId = sourceSelect.value;
                    codeReader.reset(); // these 2 lines are not fully tested
                    startScanning(codeReader, selectedDeviceId);
                };

                const sourceSelectPanel = document.getElementById('sourceSelectPanel')
                sourceSelectPanel.style.display = 'block'
            }

            startScanning(codeReader, selectedDeviceId);

        })
        .catch((err) => {
            console.error(err)
        })
})

function swapCamera(){
    let ids = [];
    let sources = document.getElementById("sourceSelect").childNodes;
    let current_source = document.getElementById("sourceSelect").value;
    for (i=0;i<sources.length;i++){
        ids.push(sources[i].value);
    }
    let current_index = ids.indexOf(current_source);
    console.log(ids);
    console.log(current_index);

    // increase index by 1
    if (current_index+1 == ids.length) document.getElementById("sourceSelect").value = ids[0];
    else document.getElementById("sourceSelect").value = ids[current_index+1];
    document.getElementById("sourceSelect").onchange();

}