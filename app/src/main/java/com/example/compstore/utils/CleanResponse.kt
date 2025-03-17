package com.example.compstore.utils

fun cleanResponse(response: String): String {
    // Удаляем полностью завершённые блоки <think>...</think>
    var cleaned = response.replace(Regex("<think>.*?</think>", RegexOption.DOT_MATCHES_ALL), "")
    // Удаляем незавершённый блок, если открыт тег <think>, но закрывающий тег ещё не поступил
    cleaned = cleaned.replace(Regex("<think>.*", RegexOption.DOT_MATCHES_ALL), "")
    return cleaned.trim()
}