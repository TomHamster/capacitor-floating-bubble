import { FloatingBubble } from 'capacitor-floating-bubble';

window.showBubble = () => {
    FloatingBubble.showBubble().then();
}

window.closeBubble = () => {
    FloatingBubble.closeBubble().then()
}

window.sendToBubble =() =>{
    FloatingBubble.sendToBubble({ message: "Hi, I'm from Capacitor View" });
}

FloatingBubble.addListener('onBubbleMessage', (message) => {
    document.querySelector('#text').innerText  += JSON.stringify(message)
});
