# capacitor-floating-bubble

## Install

```bash
npm install capacitor-floating-bubble
npx cap sync
```

## FloatingBubblePlugin API

<docgen-index>

* [`showBubble()`](#showbubble)
* [`closeBubble()`](#closebubble)
* [`addListener('onBubbleMessage', ...)`](#addlisteneronbubblemessage-)
* [`sendToBubble(...)`](#sendtobubble)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### showBubble()

```typescript
showBubble() => Promise<void>
```

Show Floating Bubble

--------------------


### closeBubble()

```typescript
closeBubble() => Promise<void>
```

Hide Floating Bubble

--------------------


### addListener('onBubbleMessage', ...)

```typescript
addListener(type: 'onBubbleMessage', listener: (data: { message: string; }) => void) => void
```

Add Listener form 'onBubbleMessage' event , message from Bubble View

| Param          | Type                                                 |
| -------------- | ---------------------------------------------------- |
| **`type`**     | <code>'onBubbleMessage'</code>                       |
| **`listener`** | <code>(data: { message: string; }) =&gt; void</code> |

--------------------


### sendToBubble(...)

```typescript
sendToBubble(message: { message: string; }) => void
```

Send Message to Bubble

| Param         | Type                              |
| ------------- | --------------------------------- |
| **`message`** | <code>{ message: string; }</code> |

--------------------

## FloatingBubbleWebViewMessenger API

<docgen-index>

* [`sendMessage(...)`](#sendmessage)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### sendMessage(...)

```typescript
sendMessage(message: string) => void
```

Sends a message to Capacitor event 'onBubbleMessage'

| Param         | Type                |
| ------------- | ------------------- |
| **`message`** | <code>string</code> |

--------------------

</docgen-api>
