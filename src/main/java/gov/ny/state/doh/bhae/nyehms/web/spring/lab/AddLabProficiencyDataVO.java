package gov.ny.state.doh.bhae.nyehms.web.spring.domain.lab;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * This class will add new filters to the PFI.
 *
 * @author vxp06
 */
public class AddLabProficiencyDataVO {

    private Connection conn;

    /**
     * @Inheritdoc
     * This will set the connection object.
     *
     * @param conn
     */
    public void setDatabaseConnection(Connection conn) {
        this.conn = conn;
    }

    /**
     * This method will accept a JsonArray editData with elements as new rows to
     * the filters and user id for database housekeeping fields. This will
     * insert new rows into database.
     *
     * @param editData
     * @param userId
     * @return
     */
    public String addLabProficiencyData(JsonArray editData, String filterType, String userId) {

        if (conn == null) {
            throw new IllegalArgumentException("database connection is null");
        }

        Statement statement = null;
        ResultSet rs = null;
        CallableStatement callableStatement = null;
        String filterNum = "";
        String PFI = editData.get(0).getAsJsonObject().get("PFI").getAsString();
        try {

            // 1. Get PFI from JSON Obj and execute procedure to get Filter Id
            String procCallSql = "{call  pkg_lab_maintenance.pr_get_filter_id_PROFICIENCY(?,?,?,?,?)}";
            callableStatement = conn.prepareCall(procCallSql);
            callableStatement.setString(1, PFI);
            callableStatement.setString(2, userId);
            callableStatement.registerOutParameter(3, java.sql.Types.NUMERIC);
            callableStatement.registerOutParameter(4, java.sql.Types.NUMERIC);
            callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);

// execute getDBUSERCursor store procedure
            callableStatement.executeUpdate();

// get cursor and cast it to ResultSet
            int filterNumInt = callableStatement.getInt(3);

            filterNum = Integer.toString(filterNumInt);

            // use the filter number to insert the records
            statement = conn.createStatement();

            for (int i = 0; i < editData.size(); i++) {

                JsonObject jsonObject = editData.get(i).getAsJsonObject();
                System.out.println("jsob obj " + editData.toString());
                String FILTER_NUMBER = filterNum;
                String FIELD_NAME = jsonObject.get("FIELD_NAME").getAsString();
                String CONDITION = jsonObject.get("CONDITION").getAsString();
                String VALUE = jsonObject.get("VALUE").getAsString();
                String OPERATOR = jsonObject.get("OPERATOR").getAsString();
                String CONDITION_ID = jsonObject.get("CONDITION_ID").getAsString();

                String query = "";

                if (jsonObject.get("processed").getAsString().equals("N")) {
                    System.out.println("in new row query");
                    // its a newly added row
                    query = "INSERT INTO LAB_PROCESS_TEST_FILTERS  "
                            + "                (FILTER_NUMBER,FIELD_NAME,CONDITION,VALUE,OPERATOR,FLAG_ACTIVE,CREATED_BY,CREATED_DT, FILTER_TYPE) "
                            + "                VALUES "
                            + "                (" + FILTER_NUMBER + ",'" + FIELD_NAME + "','" + CONDITION + "','" + VALUE + "','" + OPERATOR + "','" + 'Y' + "','" + userId + "',SYSDATE,'"+filterType+"') ";

                }

                if (!query.equals("")) {
                    statement.executeUpdate(query);
                    conn.commit();
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(LabProficiencyConditionVO.class.getName()).log(Level.SEVERE, null, ex);
            return "Error " + ex.getMessage();
        } finally {
            try {
            	if (conn != null) {
                	conn.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException sqlException) {
                Logger.getLogger(LabProficiencyConditionVO.class.getName()).log(Level.SEVERE, null, sqlException);
            }
        }
        return "Test Filters Successfully Added.";
    }
}
