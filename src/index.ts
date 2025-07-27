import { registerPlugin } from '@capacitor/core';
import { FloatingBubblePlugin } from "./definitions";


const FloatingBubble = registerPlugin<FloatingBubblePlugin>('FloatingBubble', {
  web: () => import('./web').then((m) => new m.FloatingBubbleWeb()),
});

export * from './definitions';
export { FloatingBubble };
