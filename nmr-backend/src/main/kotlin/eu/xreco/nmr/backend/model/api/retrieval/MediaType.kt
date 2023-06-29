package eu.xreco.nmr.backend.model.api.retrieval

enum class MediaType(t: String) {
    VIDEO("VIDEO"),
    IMAGES("IMAGES"),
    AUDIO("AUDIO"),
    MODEL3D("MODEL3D"),
    UNKNOWN("UNKNOWN"),
}


fun getMediaType(i: Int): MediaType {
    when(i){
        0 -> return MediaType.VIDEO
        1 -> return MediaType.IMAGES
        2 -> return MediaType.AUDIO
        3 -> return MediaType.MODEL3D
    }
    return MediaType.UNKNOWN
}