package com.smart.smartDB00.controller;

import com.smart.smartDB00.dto.common.ResResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 99902020 on 2020/3/9.
 */
@CrossOrigin
@RestController
@RequestMapping("/sqlQuery")
public class SqlQueryController {

    @Autowired
    private DataSource dataSource;

    @Value(value = "${usedDatabase}")
    private String usedDatabase;

    private void close(Connection conn, Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (Exception e2) {
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e3) {
                }
            }
        }
    }

    private static String getUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * @param set
     * @param table
     * @param idType 默认auto自增策略 seq:序列生成,uuid: uuid生成
     * @param idName 非auto自增策略  需指定 id 名称
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public ResResult insert(HttpServletRequest request, String set, String seat, String table, String idType, String idName) {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (StringUtils.isEmpty(idType))
                idType = "auto";//默认自增
            conn = dataSource.getConnection();

            String insertId = null;
            if ("seq".equalsIgnoreCase(idType)) {
                StringBuilder seqSql = new StringBuilder();
                if ("mysql".equalsIgnoreCase(usedDatabase)) {
                    seqSql.append("CALL getNextSEQ('").append(table).append("',@temp);SELECT @temp;");
                    ps = conn.prepareStatement(seqSql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ps.executeUpdate();
                    rs = ps.getResultSet();
                    rs.last();
                    insertId = String.valueOf(rs.getLong(1));
                } else if ("oracle".equalsIgnoreCase(usedDatabase)) {
                    seqSql.append("select SEQ_").append(table).append(".nextval from dual");
                    ps = conn.prepareStatement(seqSql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    rs = ps.executeQuery();
                    rs.last();
                    insertId = String.valueOf(rs.getLong(1));
                }
            } else if ("uuid".equalsIgnoreCase(idType)) {
                insertId = getUUID();
            }
            Boolean mark = Boolean.FALSE;
            if (set.contains("$,")) {
                mark = Boolean.TRUE;
                set = set.replace("$,", "$?");
            }
            String[] mappings = set.split(",");
            if (mark) {//是否需要转义
                for (int i = 0; i < mappings.length; i++) {
                    if (mappings[i].contains("$?")) {
                        mappings[i] = mappings[i].replace("$?", ",");
                    }
                }
            }
            StringBuilder insertSql = new StringBuilder("INSERT INTO ");
            insertSql.append(table);
            StringBuilder fieldsSql = new StringBuilder(" (");
            StringBuilder valuesSql = new StringBuilder(" values(");
            //对id赋值
            if (!StringUtils.isEmpty(idType) && !StringUtils.isEmpty(idName)) {
                fieldsSql.append(idName).append(",");
                valuesSql.append("'").append(insertId).append("',");
            }
            for (int i = 0; i < mappings.length; i++) {
                String mapping = mappings[i];
                String[] field_value = mapping.split("=");
                fieldsSql.append(field_value[0]);
                if (field_value.length < 1 || StringUtils.isEmpty(field_value[1]) || field_value[1].equals("''") || field_value[1].equals("'null'") || field_value[1].equals("'undefined'")) {
                    valuesSql.append("null");
                } else {
                    valuesSql.append(field_value[1]);
                }
                if (i != mappings.length - 1) {
                    fieldsSql.append(",");
                    valuesSql.append(",");
                }
            }
            fieldsSql.append(")");
            valuesSql.append(")");

            insertSql.append(fieldsSql).append(valuesSql);
            if ("auto".equalsIgnoreCase(idType)) {
                ps = conn.prepareStatement(insertSql.toString(), Statement.RETURN_GENERATED_KEYS);
                if (!StringUtils.isEmpty(seat)) {
                    String[] seatArray = seat.split("&");
                    for (int i = 0; i < seatArray.length; i++) {
                        ps.setString(i + 1, seatArray[i]);
                    }
                }
                ps.executeUpdate();
                if ("mysql".equalsIgnoreCase(usedDatabase)) {
                    rs = ps.getGeneratedKeys();
                    rs.last();
                    insertId = String.valueOf(rs.getLong(1));
                }
            } else {
                ps = conn.prepareStatement(insertSql.toString());
                if (!StringUtils.isEmpty(seat)) {
                    String[] seatArray = seat.split("&");
                    for (int i = 0; i < seatArray.length; i++) {
                        ps.setString(i + 1, seatArray[i]);
                    }
                }
                ps.executeUpdate();
            }
            resResult.setDetail(insertId);

        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(e.getMessage());
        } finally {
            close(conn, ps, rs);
        }
        return resResult;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResResult delete(String table, String where) {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            StringBuilder sql = new StringBuilder("DELETE FROM ");
            sql.append(table);
            sql.append(" WHERE ").append(where);
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql.toString());
            int rs = ps.executeUpdate();
            resResult.setDetail(rs);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(e.getMessage());
        } finally {
            close(conn, ps, null);
        }
        return resResult;
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResResult update(String table, String set, String seat, String where) {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Boolean mark = Boolean.FALSE;
            if (set.contains("$,")) {
                mark = Boolean.TRUE;
                set = set.replace("$,", "$?");
            }
            String[] mappings = set.split(",");
            if (mark) {//是否需要转义
                for (int i = 0; i < mappings.length; i++) {
                    if (mappings[i].contains("$?")) {
                        mappings[i] = mappings[i].replace("$?", ",");
                    }
                }
            }
            StringBuilder updateSql = new StringBuilder();
            for (int i = 0; i < mappings.length; i++) {
                String mapping = mappings[i];
                String[] field_value = mapping.split("=");
                if (!(field_value.length < 1 || StringUtils.isEmpty(field_value[1]) || field_value[1].equals("''") || field_value[1].equals("'null'") || field_value[1].equals("'undefined'"))) {
                    updateSql.append(mapping);
                    updateSql.append(",");
                } else {
                    updateSql.append(String.format("%s=%s", field_value[0], null));
                    updateSql.append(",");
                }
            }
            StringBuilder sql = new StringBuilder("UPDATE ");
            sql.append(table).append(" SET ");
            sql.append(updateSql.substring(0, updateSql.length() - 1));
            sql.append(" WHERE ").append(where);
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql.toString());
            if (!StringUtils.isEmpty(seat)) {
                String[] seatArray = seat.split("&");
                for (int i = 0; i < seatArray.length; i++) {
                    ps.setString(i + 1, seatArray[i]);
                }
            }
            int rs = ps.executeUpdate();
            resResult.setDetail(rs);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(e.getMessage());
        } finally {
            close(conn, ps, null);
        }
        return resResult;
    }


    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public ResResult select(String fields, String table, String where, Integer pageNum,
                            @RequestParam(defaultValue = "10") int pageSize) {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Map<String, Object> dataObj = new HashMap<>();

            StringBuilder fromSql = new StringBuilder(" FROM ");
            fromSql.append(table);
            if (!StringUtils.isEmpty(where))
                fromSql.append(" WHERE ").append(where);

            conn = dataSource.getConnection();

            //分页处理
            Integer startIndex = null;
            if (pageNum != null) {
                if (pageNum <= 0) pageNum = 1;
                if (pageSize <= 0) pageSize = 10;
                Map<String, Object> pager = new HashMap<>();
                pager.put("pageNum", pageNum);
                pager.put("pageSize", pageSize);
                //计算分页
                startIndex = (pageNum - 1) * pageSize;
                StringBuilder countSql = new StringBuilder("SELECT COUNT(1) FROM (SELECT 1 ");
                countSql.append(fromSql);
                countSql.append(") T");
                ps = conn.prepareStatement(countSql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs = ps.executeQuery();
                rs.last();
                long totalRecord = rs.getLong(1);
                pager.put("totalSize", totalRecord);
                long totalPageNum = (totalRecord + pageSize - 1) / pageSize;
                pager.put("totalPage", totalPageNum);
                dataObj.put("pager", pager);
            }

            StringBuilder selectSql = new StringBuilder("SELECT ");
            selectSql.append(fields).append(fromSql);
            if (startIndex != null) {
                if (usedDatabase.equalsIgnoreCase("mysql"))
                    selectSql.append(" limit ").append(startIndex).append(",").append(pageSize);
                else if (usedDatabase.equalsIgnoreCase("oracle")) {
                    StringBuilder oraclePageSql = new StringBuilder();
                    oraclePageSql.append("select * from (select rownum r,t.* from (");
                    oraclePageSql.append(selectSql).append(") t ");
                    oraclePageSql.append(" where rownum<=").append(pageSize * pageNum).append(" )");
                    oraclePageSql.append("  where r>").append(pageSize * pageNum - pageSize);
                    selectSql = oraclePageSql;
                }
            }
            ps = conn.prepareStatement(selectSql.toString());
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            String[] cols = new String[rsmd.getColumnCount()];
            String[] colTypes = new String[rsmd.getColumnCount()];
            for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                String columnAsName = rsmd.getColumnLabel(i);
                String columnType = rsmd.getColumnTypeName(i);
                cols[i - 1] = columnAsName;
                colTypes[i - 1] = columnType;
            }
            List<Map<String, Object>> list = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> obj = new HashMap<>(cols.length);
                for (int i = 0; i < cols.length; i++) {
                    String colName = cols[i];
                    String colType = colTypes[i];
                    if (rs.getObject(colName) == null)
                        obj.put(colName, "");
                    else if ("INT".equalsIgnoreCase(colType))
                        obj.put(colName, rs.getInt(colName));
                    else if ("VARCHAR".equalsIgnoreCase(colType))
                        obj.put(colName, rs.getString(colName));
                    else if ("DATETIME".equalsIgnoreCase(colType)) {
                        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Timestamp timestamp = rs.getTimestamp(colName);
                        obj.put(colName, timestamp != null ? sdf.format(timestamp) : timestamp);
                    } else
                        obj.put(colName, rs.getString(colName));
                }
                list.add(obj);
            }
            dataObj.put("list", list);
            resResult.setDetail(dataObj);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(e.getMessage());
        } finally {
            close(conn, ps, rs);
        }
        return resResult;
    }
}
