package acc.management.accommodationmanagement.mappers;

import acc.management.accommodationmanagement.models.Accommodation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccommodationRowMapper implements RowMapper<Accommodation> {

    @Override
    public Accommodation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Accommodation accommodation = new Accommodation();
        accommodation.setAccommodation_id(rs.getInt("accommodation_id"));
        accommodation.setDescription(rs.getString("description"));
        accommodation.setSize(rs.getString("size"));
        accommodation.setAvailability(rs.getBoolean("availability"));
        accommodation.setApartment_number(rs.getString("apartment_number"));
        return accommodation;
    }
}
