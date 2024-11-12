import { EasyAds } from 'capacitor-plugin-easyads';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    EasyAds.echo({ value: inputValue })
}
