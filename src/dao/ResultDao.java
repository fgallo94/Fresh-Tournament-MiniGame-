package dao;

import dto.Human;
import dto.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultDao {

    private ConnectionInstance conn;

    public ResultDao() {
        this.conn = new ConnectionInstance();
    }

    public void saveResult(Human winner) throws Exception {
        String sq = "insert into ResultBattle(name_of_winner,drink_in_body) values (?,?)";
        try {
            conn.connect();
            PreparedStatement st = conn.getConn().prepareStatement(sq);
            st.setString(1, winner.getName());
            st.setInt(2, winner.getDrinkedBeers());
            st.executeUpdate();
        } catch (SQLException es) {
            es.printStackTrace();
        } finally {
            try {
                conn.disconnect();
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
    }

    public ArrayList<Result> getResults() {
        ArrayList<Result> results = new ArrayList<Result>();
        String sq = "select * from ResultBattle rb order by rb.id";
        try {
            conn.connect();
            PreparedStatement st = conn.getConn().prepareStatement(sq);
            ResultSet rs = st.executeQuery();
            if (rs == null) {
                System.out.println(" No hay registros en la base de datos");
            }
            while (rs.next()) {
                Result result = new Result(rs.getString("name_of_winner"), rs.getInt("drink_in_body"));
                results.add(result);
            }
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            try {
                conn.disconnect();
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
        return results;
    }
}
