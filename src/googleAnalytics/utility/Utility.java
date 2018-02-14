package googleAnalytics.utility;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.DynamicSegment;
import com.google.api.services.analyticsreporting.v4.model.OrFiltersForSegment;
import com.google.api.services.analyticsreporting.v4.model.Segment;
import com.google.api.services.analyticsreporting.v4.model.SegmentDefinition;
import com.google.api.services.analyticsreporting.v4.model.SegmentDimensionFilter;
import com.google.api.services.analyticsreporting.v4.model.SegmentFilter;
import com.google.api.services.analyticsreporting.v4.model.SegmentFilterClause;
import com.google.api.services.analyticsreporting.v4.model.SimpleSegment;

public class Utility {

	static DateRange dateRange = new DateRange();

	public static Segment buildSimpleSegment(String segmentName, String dimension, String Operator,
			String[] dimensionFilterExpression) {
		// Create Dimension Filter.
		SegmentDimensionFilter dimensionFilter = new SegmentDimensionFilter().setDimensionName(dimension)
				.setOperator(Operator).setExpressions(Arrays.asList(dimensionFilterExpression));

		// Create Segment Filter Clause.
		SegmentFilterClause segmentFilterClause = new SegmentFilterClause().setDimensionFilter(dimensionFilter);

		// Create the Or Filters for Segment.
		OrFiltersForSegment orFiltersForSegment = new OrFiltersForSegment()
				.setSegmentFilterClauses(Arrays.asList(segmentFilterClause));

		// Create the Simple Segment.
		SimpleSegment simpleSegment = new SimpleSegment().setOrFiltersForSegment(Arrays.asList(orFiltersForSegment));

		// Create the Segment Filters.
		SegmentFilter segmentFilter = new SegmentFilter().setSimpleSegment(simpleSegment);

		// Create the Segment Definition.
		SegmentDefinition segmentDefinition = new SegmentDefinition().setSegmentFilters(Arrays.asList(segmentFilter));

		// Create the Dynamic Segment.
		DynamicSegment dynamicSegment = new DynamicSegment().setSessionSegment(segmentDefinition).setName(segmentName);

		// Create the Segments object.
		Segment segment = new Segment().setDynamicSegment(dynamicSegment);
		return segment;
	}

	public static Segment buildSimpleSegment(String segmentName, String dimension, String Operator,
			String dimensionFilterExpression) {
		// Create Dimension Filter.
		SegmentDimensionFilter dimensionFilter = new SegmentDimensionFilter().setDimensionName(dimension)
				.setOperator(Operator).setExpressions(Arrays.asList(dimensionFilterExpression));

		// Create Segment Filter Clause.
		SegmentFilterClause segmentFilterClause = new SegmentFilterClause().setDimensionFilter(dimensionFilter);

		// Create the Or Filters for Segment.
		OrFiltersForSegment orFiltersForSegment = new OrFiltersForSegment()
				.setSegmentFilterClauses(Arrays.asList(segmentFilterClause));

		// Create the Simple Segment.
		SimpleSegment simpleSegment = new SimpleSegment().setOrFiltersForSegment(Arrays.asList(orFiltersForSegment));

		// Create the Segment Filters.
		SegmentFilter segmentFilter = new SegmentFilter().setSimpleSegment(simpleSegment);

		// Create the Segment Definition.
		SegmentDefinition segmentDefinition = new SegmentDefinition().setSegmentFilters(Arrays.asList(segmentFilter));

		// Create the Dynamic Segment.
		DynamicSegment dynamicSegment = new DynamicSegment().setSessionSegment(segmentDefinition).setName(segmentName);

		// Create the Segments object.
		Segment segment = new Segment().setDynamicSegment(dynamicSegment);
		return segment;
	}

	public static List<DateRange> monthlyDateRange() {

		dateRange.setStartDate("30DaysAgo");
		dateRange.setEndDate("yesterday");
		return Arrays.asList(dateRange);

	}

	public static List<DateRange> yearlyDateRange() {

		dateRange.setStartDate("2017-01-01");
		dateRange.setEndDate("yesterday");
		return Arrays.asList(dateRange);

	}
	
	public static HashMap<String,Integer> currentDate(){
		Date todayDate=new Date();
		HashMap<String,Integer> date=new HashMap<String,Integer>(); 
		Calendar dayOfyear = Calendar.getInstance();
		dayOfyear.setTime(todayDate);
		date.put("year", dayOfyear.get(Calendar.YEAR));
		date.put("month", Calendar.MONTH);
		date.put("dayOfYear", dayOfyear.get(Calendar.DAY_OF_YEAR));
		return date;
		
	}
	
	public static Calendar yesterday(){
		Calendar yesterday = Calendar.getInstance();
		 yesterday.add(Calendar.DATE, -1);
		return yesterday;

		
	}
	public static Calendar thirtyDaysAgo(){
		Calendar thirtyDaysAgoFromYesterday = Calendar.getInstance();
		thirtyDaysAgoFromYesterday.add(Calendar.DATE, -31);
		return thirtyDaysAgoFromYesterday;
	}
	
	

}
