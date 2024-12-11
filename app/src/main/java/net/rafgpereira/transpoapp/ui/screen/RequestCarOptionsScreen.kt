package net.rafgpereira.transpoapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import net.rafgpereira.transpoapp.BuildConfig
import net.rafgpereira.transpoapp.R
import net.rafgpereira.transpoapp.domain.model.Driver
import net.rafgpereira.transpoapp.domain.model.fakeDrivers
import net.rafgpereira.transpoapp.ui.common.ScaffoldAndSurface

val fakeOriginLatLng = LatLng(-23.5215624, -46.763286699999995)

//TODO disable all buttons on click and enable once request/nav finished
//TODO add navigate back arrow
@Composable
fun RequestCarOptionsScreen(
    modifier: Modifier,
    drivers: Array<Driver>,
    navigateToHistoryScreenAction: () -> Unit, //TODO pass driver thru action?
    navigateUpAction: (() -> Unit)?,
) = ScaffoldAndSurface(
        modifier = modifier,
        title = stringResource(R.string.requestcaroptions_screen_title),
        navigateUpAction = navigateUpAction,
    ) {
        Column {
            RouteMap(fakeOriginLatLng)
            LazyColumn(contentPadding = PaddingValues(dimensionResource(R.dimen.space))) {
                items(
                    count = drivers.size,
                    itemContent = { index ->
                        DriverCard(
                            modifier = modifier,
                            driver = drivers[index],
                            chooseAction = navigateToHistoryScreenAction,
                        )
                    },
                )
            }
        }
    }

val encodedPolyline = "v`qnCpml|Ga@~Am@rB]lAYpA]|AS`AQz@YfAUz@Ox@G^Gz@ATMlAKx@I`@EVGZC\\\\AF?H?J@LBLBPH\\\\FTFR@LBJ@^?b@@|@@pA?bA?F@N@FBDDHVVVDB?D@VDVHB?XJPHPHHFHH@@LRJXDR@FF\\\\Fr@FVT|BR`CDd@JlAVpBHt@LfALtAH`AH|@JpADdA@b@Aj@?JEzAC`AI|AC^Cf@CVGx@E`@]vCaAKcAMXoCXqCBa@Dm@@c@Bg@?E?k@A}@ASEw@OsAWiACUIi@My@AM?K?K?KBSDODQFQ@I@E@[JOV]d@k@`@e@Z[X[V]JMLO^m@Ni@Je@Fa@B_@@M@K?]Ae@Ei@CUCMEWIe@Ia@o@_EKk@Ge@Kq@SmAW{BGm@Ci@Cu@?[?Q@e@?a@NsBHsAJo@TgAZyAViAXsA\\\\wAVaAV}@rAmFTkADSh@wCF[Jg@DW@I?E?EAEXoARy@p@}CDSn@qC\\\\eB|@uDl@kCHe@Pu@|@yD\\\\oAT{@f@sBb@kBh@sBHa@BIJg@n@aCLe@Nm@H]DOBIFQHWFOFOJSLSNQBETUPOPMHGNIVMRILENEJALCLCLAHANAT?B?LAR?d@@@?`@@J?|@Bt@@r@B^AlC@H?`@A^APCXANCZCPEVEPCHCHCTINERIDCHE~@g@v@g@ROVU`@]d@g@JIZ[JKJKDCDCFAFA`@e@bAmAt@y@RSrCeDn@u@j@q@b@g@hCyChEcFrI{J|@eAn@u@j@o@^c@`EyEvGyHlDcEZ_@\\\\c@b@m@RWdA{ALSpAkBl@w@n@w@DGPS@ANQ@C`@a@f@i@`AaAn@k@VU`B}A|@}@`@c@RWRU`@k@bAuAv@oAHMz@wABCLMXk@rEgLfCwGrKsX|EgMNa@vCwHdIySL]nBeFt@kBNa@j@yANYnBmFjAaD`AeCRc@Ri@z@wBTq@Tm@Zs@LUf@aAZi@^k@Ze@d@k@V[`@c@h@k@b@a@d@a@XUj@a@XQd@[f@Yr@_@|@_@FCf@S\\\\KZKf@Oj@QfCq@fFsAbCo@rGcBfEoA@C`@ODCJEh@WjC_BLGhAu@b@c@j@i@^_@b@a@b@a@LK\\\\Yb@[z@k@JG^WTOPQdA_ARUHIBADAXCxBaCTWPWLM|@aAt@w@^a@h@g@p@k@p@i@z@m@`Am@vAs@vAs@bF_C|BgADCzBcAfCmAfCmA|E_CDA^Or@[`@Ov@_@hB}@n@[f@KPGv@YjBo@XKTE\\\\GPCf@Cl@Ap@@vABlAJXDV\\\\@@BDDH@D@B?D?D?@AJADCFCBE@EBC@G@WBs@CS?IAMCUMGGAAACCG?S?GAIAIEK?M?MCmCAa@?C?G?]EyCAQ?SCUAOMm@EIOQm@o@SUc@e@a@a@OOi@]YYIGwA{Ai@m@CG[[SSOCA?AAECQQm@q@CCg@k@c@g@eAkASSEEMMKM[YY[gBiBs@u@Y[s@u@GKGIQQSQ]a@q@w@{@}@KQIMCIAAESCO?GCM?o@?EDkB?]Ag@@I?OHe@@C@CHKB_B?S@uA@c@@c@?I@Y?S?WAYAQA]C]E]E[ESESCMCICQEOAIKWISEKSSkA_C]m@sAmCwBcEGOqAgCyAsCKMc@{@ISGMIUGUCMAGAGAC?C?A@A@AAGCWCMCQ?Y?Q@s@?YA[AGGe@AG?EG_@G]CMOg@m@_A]g@ACMSy@oAu@kAc@s@qAqBk@}@a@o@iAeBeA_BMQKOOUIOcAaBc@o@kAgBa@m@U]m@{@ACQYIMMQs@iAkAiBq@aAIQOYW_@a@m@GIQWMOKQMQQQk@k@[Yg@g@[YUSsEkEUUOOu@u@A?o@m@WWOOMKIKSQw@u@][UWUUu@s@_@_@WWk@i@c@a@a@]MMGGIIGGAG?CCGMUACCCEEKIOIOKIEa@QYKKCUIEAICIEGC[^MPSVUZCDOP[b@QTu@y@UWKKAAm@o@e@e@o@q@CCo@q@k@o@ECSUGGKKm@t@CDIHUXCD"
val mapsApiKey = BuildConfig.MAPS_API_KEY
val staticMapUrl = "https://maps.googleapis.com/maps/api/staticmapsize=400x400&center=59.900503,-135.478011&zoom=4&path=weight:3%7Ccolor:orange%7Cenc:${encodedPolyline}&key=${mapsApiKey}"

//TODO replace with image loading static map url
@Composable
fun RouteMap(originPosition: LatLng) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(originPosition, 15f)
    }
    GoogleMap(
        modifier = Modifier.height(300.dp),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            compassEnabled = false,
            indoorLevelPickerEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false,
            rotationGesturesEnabled = false,
            scrollGesturesEnabled = false,
            scrollGesturesEnabledDuringRotateOrZoom = false,
            tiltGesturesEnabled = false,
            zoomControlsEnabled = false,
            zoomGesturesEnabled = false
        ),
    )
}

@Composable
fun DriverCard(
    modifier: Modifier,
    driver: Driver,
    chooseAction: () -> Unit,
) = ElevatedCard(
    modifier = modifier.padding(dimensionResource(R.dimen.space)),
    colors = CardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = Color.Black,
        disabledContainerColor = Color.DarkGray,
        disabledContentColor = Color.DarkGray
    )
) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.double_space)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.half_unit)),
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            LabelAndInformation(stringResource(R.string.requestcaroptions_name_label), driver.name)
            driver.vehicle?.let {
                LabelAndInformation(
                    stringResource(R.string.requestcaroptions_vehicle_label), it
                )
            }
            LabelAndInformation(
                stringResource(R.string.requestcaroptions_rating_label),
                    driver.review?.rating.toString()
            )
        }
        if (driver.description?.isEmpty()?.not() == true) {
            LabelAndInformation(
                stringResource(R.string.requestcaroptions_desc_label),
                driver.description
            )
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            LabelAndInformation(stringResource(R.string.requestcaroptions_cost_label), driver.value.toString())
            //TODO add on-going request animation
            Button(
                modifier = modifier.width(dimensionResource(R.dimen.button_width)),
                onClick = chooseAction,
                contentPadding = PaddingValues(dimensionResource(R.dimen.zero_dp)),
            ) {
                Text(
                    text = stringResource(R.string.requestcaroptions_choose_button_text),
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
fun LabelAndInformation(label: String, info: String) =
    Column {
        Text(label)
        Text(
            text = info,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
    }

@Composable
@Preview(showSystemUi = false, showBackground = true)
fun LabelAndInformationPreview() = LabelAndInformation("nome", fakeDrivers[0].name)

@Composable
@Preview(showSystemUi = false, showBackground = false)
fun DriverCardPreview() = DriverCard(Modifier, fakeDrivers[0]) {}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun RequestCarOptionsScreenPreview() = RequestCarOptionsScreen(
    Modifier, fakeDrivers, {}) {}