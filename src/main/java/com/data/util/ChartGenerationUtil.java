package com.data.util;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

@Component
public class ChartGenerationUtil {
	public void generatePieChart(String location, String fileName, List<Object[]> CountList) {
		DefaultPieDataset userTypeDataSet = new DefaultPieDataset();
		for(Object[] userTypeCount:CountList) {
			userTypeDataSet.setValue(userTypeCount[0].toString(), (Number) userTypeCount[1]);
		}
		
		PieSectionLabelGenerator label = new StandardPieSectionLabelGenerator("{0} = {1}, {2}");
		JFreeChart userTypeChart = ChartFactory.createPieChart("", userTypeDataSet);
		userTypeChart.setBackgroundPaint(Color.CYAN);
		PiePlot plot = (PiePlot) userTypeChart.getPlot();
		plot.setLabelGenerator(label);
		plot.setSimpleLabels(true);
		
		
		try {
			ChartUtils.saveChartAsPNG(new File(location + fileName), userTypeChart, 400, 300);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void generateBarChart(String location, String fileName, List<Object[]> countList, String xTitle) {
		DefaultCategoryDataset userTypeDataSet = new DefaultCategoryDataset();
		for(Object[] count:countList) {
			userTypeDataSet.setValue((Number)count[1], count[0].toString(), "");
		}
		JFreeChart userTypeChart = ChartFactory.createBarChart("", xTitle, "Count", userTypeDataSet);
		userTypeChart.setBackgroundPaint(Color.CYAN);
		try {
			ChartUtils.saveChartAsPNG(new File(location + fileName), userTypeChart, 400, 300);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
