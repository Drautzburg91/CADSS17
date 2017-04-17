package cad.cep.statistics;

import java.util.Map;

/**
 * The Interface IStatistic.
 */
//TODO needs Concept
public interface IStatistic {

	Map<String, String> getValues();
	
	IStatistic addEntry(String key, String value);
	
}
