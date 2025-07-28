import Foundation

@objc public class FloatingBubble: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
