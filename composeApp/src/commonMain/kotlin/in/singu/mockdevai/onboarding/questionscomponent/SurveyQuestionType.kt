package `in`.singu.mockdevai.onboarding.questionscomponent


sealed class SurveyQuestionType {
    data class SingleChoice(
        val question: String,
        val options: List<String>
    ) : SurveyQuestionType()

    data class TextInput(
        val question: String
    ) : SurveyQuestionType()

    data class FileUpload(
        val question: String
    ) : SurveyQuestionType()

    data class FeaturePromo(
        val title: String,
        val description: String,
        val stats: List<Pair<String, Float>>,
        val chartType: ChartType
    ) : SurveyQuestionType()
}

val questions = listOf(
    SurveyQuestionType.FeaturePromo(
        title = "Personalized Learning Path",
        description = "Get a customized learning experience based on your skill level and goals:",
        stats = listOf(
            "Learning Efficiency" to 0.95f,
            "Skill Improvement" to 0.85f,
            "Project Completion Rate" to 0.80f
        ),
        chartType = ChartType.GROWTH_LINE
    ),
    SurveyQuestionType.TextInput(
        "What is your good name?"
    ),
    SurveyQuestionType.SingleChoice(
        "What is your current career stage?",
        listOf("Student", "Entry-level", "Mid-level", "Senior-level")
    ),
    SurveyQuestionType.SingleChoice(
        "What kind of software roles are you interested in?",
        listOf(
            "AI/ML Engineer",
            "Data Scientist",
            "Mobile Developer",
            "Frontend Developer",
            "Backend Developer",
            "Full-Stack Developer",
            "DevOps Engineer",
            "QA Engineer",
            "Network Engineer",
            "Database Administrator",
            "Cloud Engineer",
            "Robotics Engineer"
        )
    ),
    SurveyQuestionType.SingleChoice(
        "How would you rate your current technical skills?",
        listOf("Beginner", "Intermediate", "Advanced")
    ),
    SurveyQuestionType.FileUpload(
        "Please upload your updated resume in PDF format"
    ),
    SurveyQuestionType.SingleChoice(
        "When was your last interview?",
        listOf("Less than a week ago", "1-2 weeks ago", "1 month ago", "More than a month ago")
    ),
    SurveyQuestionType.SingleChoice(
        "How many interviews have you given in the past 6 months?",
        listOf("0 - 3", "4 - 6", " 7 - 10", "More than 10")
    ),
    SurveyQuestionType.SingleChoice(
        "Do you feel ready for your next technical interview?",
        listOf(
            "Yes, I'm confident in my skills.",
            "No, I don't feel prepared yet.",
            " I'm not sure; I need help identifying my weaknesses."
        )
    ),
    SurveyQuestionType.SingleChoice(
        "Do you struggle with keeping track of which interview topics you've mastered and which you still need to improve?",
        listOf(
            "Yes, it's hard to stay organized with everything.",
            "No, I can keep track of my progress easily.",
            "Sometimes, I forget which areas need more focus."
        )
    ),
    SurveyQuestionType.SingleChoice(
        "Are you finding it hard to get constructive feedback after practicing interview questions?",
        listOf(
            "Yes, I don't get enough feedback to know where I'm going wrong.",
            "No, I get clear feedback from friends/mentors.",
            "Sometimes, but it's not detailed enough to be helpful."
        )
    ),
    SurveyQuestionType.SingleChoice(
        "Have you ever felt unprepared or nervous during a real interview?",
        listOf(
            "Yes, I often feel nervous and struggle with questions.",
            "Sometimes, I get nervous but can still handle the interview.",
            "No, I'm always calm and confident."
        )
    ),
    SurveyQuestionType.SingleChoice(
        "How much time do you have left to prepare for your next interview?",
        listOf(
            "Less than a week",
            "1 - 2 weeks",
            "2 - 4 weeks",
            "More than a month"
        )
    ),
    SurveyQuestionType.SingleChoice(
        "How much time can you realistically dedicate to preparation each week?",
        listOf("Less than 2 hours", "2 - 5 hours", "5 - 10 hours", "More than 10 hours")
    ),
    SurveyQuestionType.SingleChoice(
        "Would you like to track your progress and see how you're improving over time?",
        listOf(
            "Yes, I'd love to track my performance",
            "No, I don't think I need that.",
            "I'm not sure yet."
        )
    ),
    SurveyQuestionType.SingleChoice(
        "How did you heard about us?",
        listOf(
            "Instagram",
            "Tiktok",
            "Friends/Mentor Suggestion",
            "Random Search",
            "Others"
        )
    ),
    SurveyQuestionType.FeaturePromo(
        title = "Boost Your Interview Confidence",
        description = "See how our AI-powered mock interviews can boost your confidence in just 4 weeks:",
        stats = listOf(
            "Interview Readiness" to 0.95f,
            "Technical Communication" to 0.90f,
            "Problem-Solving Speed" to 0.90f
        ),
        chartType = ChartType.CONFIDENCE_BAR
    ),
    SurveyQuestionType.FeaturePromo(
        title = "Track Your Progress",
        description = "Monitor your improvement with our comprehensive tracking system:",
        stats = listOf(
            "Overall Progress" to 0.85f,
            "Skills Mastered" to 0.75f,
            "Practice Completion" to 0.90f
        ),
        chartType = ChartType.PROGRESS_CIRCLE
    ),
)