import { useState } from 'react';

export default function Footer() {
    const [feedbackOpen, setFeedbackOpen] = useState(false);
    const [feedbackType, setFeedbackType] = useState('bug');
    const [feedbackText, setFeedbackText] = useState('');
    const [submitted, setSubmitted] = useState(false);

    const handleSubmit = () => {
        if (!feedbackText.trim()) return;
        // For now logs to console — you can wire this to an API endpoint later
        console.log('Feedback submitted:', { type: feedbackType, message: feedbackText });
        setSubmitted(true);
        setFeedbackText('');
        setTimeout(() => {
            setSubmitted(false);
            setFeedbackOpen(false);
        }, 2500);
    };

    return (
        <footer className="bg-green-800 text-white mt-8">

            {/* Feedback modal */}
            {feedbackOpen && (
                <div className="fixed inset-0 bg-black/50 flex items-end justify-center z-50 p-4">
                    <div className="bg-white rounded-xl w-full max-w-md p-5 text-gray-800">
                        <div className="flex items-center justify-between mb-4">
                            <h3 className="font-bold text-lg text-green-800">Send Feedback</h3>
                            <button
                                onClick={() => setFeedbackOpen(false)}
                                className="text-gray-400 hover:text-gray-600 text-xl font-bold"
                            >
                                ✕
                            </button>
                        </div>

                        {submitted ? (
                            <div className="text-center py-6">
                                <div className="text-4xl mb-2">✅</div>
                                <p className="text-green-700 font-semibold">Thanks for your feedback!</p>
                                <p className="text-sm text-gray-500 mt-1">We'll review it shortly.</p>
                            </div>
                        ) : (
                            <>
                                {/* Feedback type selector */}
                                <div className="flex gap-2 mb-4">
                                    {[
                                        { value: 'bug', label: '🐛 Bug' },
                                        { value: 'suggestion', label: '💡 Suggestion' },
                                        { value: 'other', label: '💬 Other' },
                                    ].map(option => (
                                        <button
                                            key={option.value}
                                            onClick={() => setFeedbackType(option.value)}
                                            className={`flex-1 py-2 px-3 rounded-lg text-sm font-medium border transition ${
                                                feedbackType === option.value
                                                    ? 'bg-green-700 text-white border-green-700'
                                                    : 'bg-white text-gray-600 border-gray-300 hover:bg-gray-50'
                                            }`}
                                        >
                                            {option.label}
                                        </button>
                                    ))}
                                </div>

                                {/* Feedback text */}
                                <textarea
                                    value={feedbackText}
                                    onChange={e => setFeedbackText(e.target.value)}
                                    placeholder="Describe your feedback here..."
                                    rows={4}
                                    className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-green-500 resize-none mb-4"
                                />

                                <button
                                    onClick={handleSubmit}
                                    disabled={!feedbackText.trim()}
                                    className="w-full bg-green-700 hover:bg-green-800 disabled:bg-gray-300 disabled:cursor-not-allowed text-white py-3 rounded-lg font-semibold transition"
                                >
                                    Submit Feedback
                                </button>
                            </>
                        )}
                    </div>
                </div>
            )}

            {/* Footer content */}
            <div className="max-w-4xl mx-auto px-6 py-8">
                <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-6">

                    {/* Brand */}
                    <div>
                        <div className="flex items-center gap-2 mb-3">
                            <div className="w-8 h-8 bg-white rounded-full flex items-center justify-center">
                                <svg className="text-green-700" width="18" height="18" fill="currentColor" viewBox="0 0 24 24">
                                    <path d="M5 11l1.5-4.5h11L19 11m-1.5 5h-11L5 11h14l-1.5 5M12 18.5A1.5 1.5 0 0 1 10.5 17 1.5 1.5 0 0 1 12 15.5a1.5 1.5 0 0 1 1.5 1.5 1.5 1.5 0 0 1-1.5 1.5m-5 0A1.5 1.5 0 0 1 5.5 17 1.5 1.5 0 0 1 7 15.5 1.5 1.5 0 0 1 8.5 17 1.5 1.5 0 0 1 7 18.5z"/>
                                </svg>
                            </div>
                            <span className="font-bold text-lg">Smart Parking+</span>
                        </div>
                        <p className="text-green-200 text-sm leading-relaxed">
                            Real-time parking availability for UNC Charlotte. 
                            Find Parking, Not Frustration.
                            <a 
                                href="https://smartparkingplus.lovable.app/" 
                                target="_blank" 
                                rel="noopener noreferrer" 
                                className="inline-block mt-3 text-green-300 hover:text-white text-sm underline underline-offset-2 transition">
                                Learn More about Smart Parking+ ↗ 
                            </a>
                        </p>
                    </div>

                    {/* Contact */}
                    <div>
                        <h4 className="font-semibold text-green-300 mb-3 uppercase text-xs tracking-wider">
                            Contact
                        </h4>
                        <ul className="space-y-2 text-sm text-green-100">
                            <li className="flex items-center gap-2">
                                <svg width="14" height="14" fill="currentColor" viewBox="0 0 24 24">
                                    <path d="M20,8L12,13L4,8V6L12,11L20,6M20,4H4C2.89,4 2,4.89 2,6V18A2,2 0 0,0 4,20H20A2,2 0 0,0 22,18V6C22,4.89 21.1,4 20,4Z"/>
                                </svg>
                                <a href="mailto:zurimckee95@gmail.com" className="hover:text-white transition">
                                    zurimckee95@gmail.com
                                </a>
                            </li>
                            <li className="flex items-center gap-2">
                                <svg width="14" height="14" fill="currentColor" viewBox="0 0 24 24">
                                    <path d="M12,11.5A2.5,2.5 0 0,1 9.5,9A2.5,2.5 0 0,1 12,6.5A2.5,2.5 0 0,1 14.5,9A2.5,2.5 0 0,1 12,11.5M12,2A7,7 0 0,0 5,9C5,14.25 12,22 12,22C12,22 19,14.25 19,9A7,7 0 0,0 12,2Z"/>
                                </svg>
                                <span>UNC Charlotte, NC</span>
                            </li>
                        </ul>
                    </div>

                    {/* Feedback */}
                    <div>
                        <h4 className="font-semibold text-green-300 mb-3 uppercase text-xs tracking-wider">
                            Help Us Improve
                        </h4>
                        <p className="text-sm text-green-200 mb-3">
                            Found a bug or have a suggestion? We'd love to hear from you.
                        </p>
                        <button
                            onClick={() => setFeedbackOpen(true)}
                            className="flex items-center gap-2 bg-white text-green-800 hover:bg-green-100 px-4 py-2 rounded-lg text-sm font-semibold transition"
                        >
                            <svg width="16" height="16" fill="currentColor" viewBox="0 0 24 24">
                                <path d="M20,2H4A2,2 0 0,0 2,4V22L6,18H20A2,2 0 0,0 22,16V4A2,2 0 0,0 20,2M6,9H18V11H6M14,14H6V12H14M18,8H6V6H18"/>
                            </svg>
                            Send Feedback
                        </button>
                    </div>
                </div>

                {/* Bottom bar */}
                <div className="border-t border-green-700 pt-4 flex flex-col md:flex-row items-center justify-between gap-2 text-xs text-green-400">
                    <span>© {new Date().getFullYear()} Smart Parking+. Julius, Marcos, Mac & Jay.</span>
                </div>
            </div>
        </footer>
    );
}