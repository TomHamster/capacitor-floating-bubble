export interface FloatingBubblePlugin {
    /**
     * Show Floating Bubble
     */
    showBubble(): Promise<void>;
    /**
     * Hide Floating Bubble
     */
    closeBubble(): Promise<void>;

    /**
     * Add Listener form 'onBubbleMessage' event , message from Bubble View
     */
    addListener(type: 'onBubbleMessage', listener: (data: { message: string }) => void): void;

    /**
     * Send Message to Bubble
     */
    sendToBubble(message: { message: string }): void;
}


export interface FloatingBubbleWebViewMessenger {
    /**
     * Sends a message to Capacitor event 'onBubbleMessage'
     */
    sendMessage(message: string): void
}
