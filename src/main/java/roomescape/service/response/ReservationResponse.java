package roomescape.service.response;

import roomescape.domain.Reservation;

public record ReservationResponse(
        long id,
        String name,
        String date,
        ReservationTimeResponse time,
        ThemeResponse theme
) {

    public static ReservationResponse from(Reservation reservation) {
        String date = reservation.getDate().toString();
        ReservationTimeResponse timeResponse = ReservationTimeResponse.from(reservation.getTime());
        ThemeResponse themeResponse = ThemeResponse.from(reservation.getTheme());

        return new ReservationResponse(
                reservation.getId(),
                reservation.getName().value(),
                date,
                timeResponse,
                themeResponse
        );
    }
}
