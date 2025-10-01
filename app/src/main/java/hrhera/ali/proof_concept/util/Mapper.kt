package hrhera.ali.proof_concept.util


inline fun <T, R> T.mapItem(mapping: (T) -> R): R {
    return mapping(this)
}
