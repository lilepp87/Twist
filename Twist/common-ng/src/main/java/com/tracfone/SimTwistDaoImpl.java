package com.tracfone;

import java.sql.Types;
import java.util.Hashtable;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.tracfone.core.persistence.dao.AbstractBaseDao;

public class SimTwistDaoImpl extends AbstractBaseDao {

	private final SimTestFunction simTestFunction;
	private JdbcTemplate clfyjdbcTemplate;

	public SimTwistDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);

		simTestFunction = new SimTestFunction();
	}

	public synchronized String getNewSimCardByPartNumber(String partNumber) {
		Map<String, String> outMap = simTestFunction.execute(partNumber);

		String result = outMap.get("return");
		logger.info("SIM: " + result);
		logger.debug("Call to " + GET_TEST_SIM_BY_PART_NUMBER);
		logger.debug("		part_number --> " + partNumber);
		logger.debug("		result --> " + result);
		return result;
	}

	public String getNewSim(String partNumber) {
		return this.getNewSimCardByPartNumber(partNumber);
	}

	private class SimTestFunction extends StoredProcedure {
		public SimTestFunction() {
			super(getDataSource(), GET_TEST_SIM_BY_PART_NUMBER);
			setFunction(true);
			declareParameter(new SqlOutParameter("return", Types.VARCHAR));
			declareParameter(new SqlParameter("sim_part_number", Types.VARCHAR));

			compile();
		}

		public Map execute(String partNumber) {
			Map inParameters = new Hashtable();
			inParameters.put("sim_part_number", partNumber);

			return execute(inParameters);
		}
	}
	public String getSimPartFromSimNumber(String sim) {
		String simPartNumber = (String) this.clfyjdbcTemplate.queryForObject(GET_SIM_PART_FROM_SIM_NUMBER, new Object[] { sim }, String.class);
		if (simPartNumber == null || simPartNumber.isEmpty()) {
			throw new IllegalArgumentException("No sim part found for  "+ sim);
		} else {
			return simPartNumber;
		}
	}
	public static String GET_TEST_SIM_BY_PART_NUMBER = "sa.get_test_sim";

	private final static String GET_SIM_PART_FROM_SIM_NUMBER="SELECT pn.part_number FROM table_x_sim_inv si, table_mod_level ml, table_part_num pn "
			+ "WHERE si.x_sim_inv2part_mod = ml.objid AND ml.part_info2part_num = pn.objid AND si.x_sim_serial_no NOT LIKE '%F%' AND LENGTH (si.x_sim_serial_no) > 18"
			+ "AND pn.s_domain = 'SIM CARDS'AND si.x_sim_serial_no = ?";
	
	
	public void setClfyjdbcTemplate(JdbcTemplate clfyjdbcTemplate) {
		this.clfyjdbcTemplate = clfyjdbcTemplate;
	}
}