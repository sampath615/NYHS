
<!DOCTYPE html>
<html>
<head>
	<title>Worker Metrics Report Details</title>

	<link href="${staticPath}/css/common.css"  rel="stylesheet" type="text/css" />
	<link href="${staticPath}/css/reports/workerMetrics/workerMetrics.css?v=${buildDate}" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="${staticPath}/js/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="${staticPath}/js/highcharts/highcharts-3d.js"></script>
    <script type="text/javascript" src="${staticPath}/js/highcharts/exporting.js"></script>
	<script type="text/javascript">	
		Highcharts.setOptions({
		    lang: {
		        decimalPoint: '.',
		        NamesTimeliness: ['Timeliness (Calculation TBD)', 'Comparison Timeliness']
		    }
		});
		$(function () {
		    $('#containerTimeliness').highcharts({
		        chart: {
		            type: 'column',
		            margin: 75,
		            options3d: {
		                enabled: true,
		                alpha: 10,
		                beta: 25,
		                depth: 70
		            },
		            borderWidth: 5,
		            borderColor: '#e8eaeb',
		            borderRadius: 10,
		            backgroundColor: '#fff'
		        },
		        title: {
		            text: '3D chart for Timeliness'
		        },
		        subtitle: {
		            text: ''
		        },
		        plotOptions: {
		            column: {
		                depth: 25
		            }
		        },
		        xAxis: {
		            categories: Highcharts.getOptions().lang.NamesTimeliness
		        },
		        yAxis: {
		            opposite: true
		        },
		        series: [{
		            name: 'Timeliness',
		            data: [{
							color: '#77a1e5',
							y: 123
						}, {
							color: '#c42525',
							y: 297
						}]
		        }]
		    });
		});
	</script>	
    
    <script type="text/javascript">	
		Highcharts.setOptions({
		    lang: {
		        decimalPoint: '.',
		        NamesQuality: ['Quality (Completeness)', 'Comparison Quality (Completeness)']
		    }
		});
		$(function () {
		    $('#containerQuality').highcharts({
		        chart: {
		            type: 'column',
		            margin: 75,
		            options3d: {
		                enabled: true,
		                alpha: 10,
		                beta: 25,
		                depth: 70
		            },
		            borderWidth: 5,
		            borderColor: '#e8eaeb',
		            borderRadius: 10,
		            backgroundColor: '#fff'
		        },
		        title: {
		            text: '3D chart for Quality'
		        },
		        subtitle: {
		            text: ''
		        },
		        plotOptions: {
		            column: {
		                depth: 25
		            }
		        },
		        xAxis: {
		            categories: Highcharts.getOptions().lang.NamesQuality
		        },
		        yAxis: {
		            opposite: true
		        },
		        series: [{
		            name: 'Quality',
		            data: [{
							color: '#77a1e5',
							y: 150
						}, {
							color: '#c42525',
							y: 82
						}]
		        }]
		    });
		});
	</script>	

	<script type="text/javascript">	
	 	$(function () {
		    // Radialize the colors
		    Highcharts.setOptions({
			    colors: ['#77a1e5', '#c42525', '#a6c96a']
			});
		    Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
		        return {
		        	
		            radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
		            stops: [
		                [0, color],
		                [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
		            ]
		        };
		    });

		    // Build the chart
		    $('#containerVolume').highcharts({
		        chart: {
		        	type: 'pie',
		            options3d: {
		                enabled: true,
		                alpha: 45,
		                beta: 0
		            },
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
		            borderWidth: 5,
		            borderColor: '#e8eaeb',
		            borderRadius: 10,
		            backgroundColor: '#fff'
		        },
		        title: {
		            text: '3D pie for Volume'
		        },
		        tooltip: {
		            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                depth: 35,
		                dataLabels: {
		                    enabled: true,
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
		                    style: {
		                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		                    },
		                    connectorColor: 'silver'
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: 'Volume',
		            data: [
		                ['Volume <br> Handled',   62.7],
		                {
		                    name: 'Comparison <br> Volume by <br> Region/State',
		                    y: 37.3,
		                    sliced: true,
		                    selected: true
		                }
		            ]
		        }]
		    });
		});
	</script>
</head>

<body>
	<div id="workerMetrics">

		<div class="workerMetricsDetails">
			<span class="fieldHolder">
				<span class="fLW45"> Worker Name:</span> 
				<span class="fLW40"> <input value='' readonly class="standardInputSmallText"> </span>
			</span>
			<span class="fieldHolder">
				<span class="fLW45"> Region:</span> 
				<span class="fLW40"> <input value='' readonly class="standardInputSmallText"> </span>
			</span>
			<br><br>
			<span class="fieldHolder">
				<span class="fLW45"> Volume Handled:</span> 
				<span class="fLW40"> <input value='37.3' readonly class="standardInputSmallText"> </span>
			</span>
			<span class="fieldHolder">
				<span class="fLW45"> Comparison Volume by Region/State</span> 
				<span class="fLW40"> <input value='62.7' readonly class="standardInputSmallText"> </span>
			</span>
			<br><br>
			<span class="fieldHolder">
				<span class="fLW45"> Timeliness (Calculation TBD):</span> 
				<span class="fLW40"> <input value='123' readonly class="standardInputSmallText"> </span>
			</span>
			<span class="fieldHolder">
				<span class="fLW45"> Comparison Timeliness:</span> 
				<span class="fLW40"> <input value='297' readonly class="standardInputSmallText"> </span>
			</span>
			<br><br>
			<span class="fieldHolder">
				<span class="fLW45"> Quality (Completeness):</span> 
				<span class="fLW40"> <input value='150' readonly class="standardInputSmallText"> </span>
			</span>
			<span class="fieldHolder">
				<span class="fLW45"> Comparison Quality (Completeness):</span> 
				<span class="fLW40"> <input value='82' readonly class="standardInputSmallText"> </span>
			</span>
			<br><br>
		</div>	

		<div id="containerVolume"></div>
		<div id="containerQuality"></div>
		<div id="containerTimeliness"></div>
			
	</div>	
</body>