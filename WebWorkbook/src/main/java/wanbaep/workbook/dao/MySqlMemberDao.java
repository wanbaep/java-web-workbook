package wanbaep.workbook.dao;

import wanbaep.workbook.vo.Member;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlMemberDao implements MemberDao {
    private DataSource ds;

    public void setDataSource(DataSource ds) {
        this.ds = ds;
    }

    public List<Member> selectList() throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = ds.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT MNO, MNAME, EMAIL, CRE_DATE FROM MEMBERS ORDER BY MNO ASC");

            ArrayList<Member> members = new ArrayList<>();
            while(rs.next()) {
                members.add(new Member()
                        .setNo(rs.getInt("MNO"))
                        .setName(rs.getString("MNAME"))
                        .setEmail(rs.getString("EMAIL"))
                        .setCreatedDate(rs.getDate("CRE_DATE")));
            }
            return members;
        } catch (Exception e) {
            throw e;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }
    }

    public int insert(Member member) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ds.getConnection();
            stmt = connection.prepareStatement(
                    "INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)" +
                            " VALUES (?,?,?,NOW(),NOW())");
            stmt.setString(1, member.getEmail());
            stmt.setString(2, member.getPassword());
            stmt.setString(3, member.getName());
            return stmt.executeUpdate();  //executeUpdate 의 return 확인 필요 -> 실패 시 throw Exception
        } catch (Exception e) {
            throw e;    //insert 실패 시 throw
        } finally {
            try {if (stmt != null) stmt.close();} catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }
    }

    public int delete(int no) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ds.getConnection();
            stmt = connection.prepareStatement("DELETE FROM MEMBERS WHERE MNO=?");
            stmt.setInt(1, no);
            return stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            try { if(stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }
    }

    public Member selectOne(int no) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = ds.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select MNO, EMAIL, MNAME, CRE_DATE from MEMBERS where MNO=" + no);

            if(rs.next()) {
                Member member = new Member()
                        .setNo(rs.getInt("MNO"))
                        .setName(rs.getString("MNAME"))
                        .setEmail(rs.getString("EMAIL"))
                        .setCreatedDate(rs.getDate("CRE_DATE"));
                return member;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace();}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }
    }

    public int update(Member member) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ds.getConnection();
            stmt = connection.prepareStatement("UPDATE MEMBERS SET EMAIL=?,MNAME=?,MOD_DATE=NOW() WHERE MNO=?");
            stmt.setString(1, member.getEmail());
            stmt.setString(2, member.getName());
            stmt.setInt(3, member.getNo());
            return stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }
    }

    public Member exist(String email, String password) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = ds.getConnection();
            stmt = connection.prepareStatement("SELECT MNAME, EMAIL FROM MEMBERS WHERE EMAIL=? AND PWD=?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if(rs.next()) {
                Member member = new Member()
                        .setEmail(rs.getString("EMAIL"))
                        .setName(rs.getString("MNAME"));
                return member;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }
    }
}
