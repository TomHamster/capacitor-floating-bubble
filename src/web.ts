import {WebPlugin} from '@capacitor/core';

import type {FloatingBubblePlugin} from './definitions';

export class FloatingBubbleWeb extends WebPlugin implements FloatingBubblePlugin {
    showBubble(): Promise<void> {
        console.log('FloatingBubble is not available on Web');
        return Promise.resolve(undefined);
    }

    closeBubble(): Promise<void> {
        console.log('FloatingBubble is not available on Web');
        return Promise.resolve(undefined);
    }

    sendToBubble(message: { message: string }): void {
        console.log(`FloatingBubble is not available on Web message: ${message}`);
    }


}
