import Capacitor
import UIKit
import WebKit

final class MisterViewController: CAPBridgeViewController {
    private static let ruleIdentifier = "mister-clean-ad-block-v1"

    override func webViewConfiguration(for instanceConfiguration: InstanceConfiguration) -> WKWebViewConfiguration {
        let configuration = super.webViewConfiguration(for: instanceConfiguration)
        let styleScript = WKUserScript(
            source: GeneratedFilters.cssInjectionJavaScript,
            injectionTime: .atDocumentEnd,
            forMainFrameOnly: false
        )
        configuration.userContentController.addUserScript(styleScript)
        return configuration
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor(red: 8 / 255, green: 11 / 255, blue: 7 / 255, alpha: 1)
        webView?.backgroundColor = view.backgroundColor
        webView?.scrollView.backgroundColor = view.backgroundColor
        webView?.scrollView.contentInsetAdjustmentBehavior = .never
        installContentBlocker()
    }

    override var prefersStatusBarHidden: Bool { true }

    private func installContentBlocker() {
        WKContentRuleListStore.default().compileContentRuleList(
            forIdentifier: Self.ruleIdentifier,
            encodedContentRuleList: GeneratedFilters.contentRuleJSON
        ) { [weak self] ruleList, _ in
            guard let self, let ruleList else { return }
            DispatchQueue.main.async {
                self.webView?.configuration.userContentController.add(ruleList)
                self.webView?.reload()
            }
        }
    }
}
