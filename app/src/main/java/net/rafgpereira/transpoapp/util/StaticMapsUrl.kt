package net.rafgpereira.transpoapp.util

import androidx.compose.ui.graphics.Color
import net.rafgpereira.transpoapp.BuildConfig
import net.rafgpereira.transpoapp.domain.model.LatLng

class StaticMapsUrl private constructor(val value: String) {
    companion object {
        private const val DELIMITER = "|"
        private const val MARKERS_MASK = "{markers}"
        private const val PATH_MASK = "{path}"
        private const val PATH_COLOR_MASK = "{pathColor}"
        private const val BASE_STATIC_MAPS_URL =
            "https://maps.googleapis.com/maps/api/staticmap?" +
                    "size=600x600" +
                    MARKERS_MASK +
                    "&path=color:$PATH_COLOR_MASK|$PATH_MASK" +
                    "&key=${BuildConfig.MAPS_API_KEY}"
    }

    class Builder {
        private var markers: String? = null
        private var pathColor: String? = null
        private var path: String? = null

        fun setMarkers(locations: List<LatLng>) : Builder {
            val markerQuery = "&markers="
            var markers = ""

            locations.forEach { location ->
                markers += "${markerQuery}${location.latitude},${location.longitude}"
            }

            this.markers = markers
            return this
        }

        fun setPathColor(pathColor: Color) = apply {
            this.pathColor = pathColor.toHexString()
            return this
        }

        fun setPath(steps: List<LatLng>) = apply {
            var path = ""

            steps.forEach { step ->
                path += "${step.latitude},${step.longitude}$DELIMITER"
            }
            path = path.replaceLast(DELIMITER, "")

            this.path = path
            return this
        }

        fun build() = StaticMapsUrl(
            BASE_STATIC_MAPS_URL
                .replace(MARKERS_MASK, markers!!)
                .replace(PATH_COLOR_MASK, pathColor!!)
                .replace(PATH_MASK, path!!)
        )
    }
}