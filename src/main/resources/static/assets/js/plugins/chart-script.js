//Options for the Combined Chart (Candlestick and Line)
var currentTimestamp = new Date().getTime();
var sixHoursAgo = currentTimestamp - 1 * 60 * 60 * 1000;


var combinedOptions = {
  grid: {
    show: true // Hide the background grid lines
  },
  
  legend: {
      show: false // Hide the legend
    },
  series: [
    {
      name: "Candlestick",
      type: "candlestick",
      data: [], // Leave this empty for now, we'll populate it later
      strokeWidth: 1
    },
    {
      name: "Line Series",
      type: "line",
      data: [], // Leave this empty for now, we'll populate it later
      strokeWidth: 1,
      color : "#25316d"
    }
  ],
  
  chart: {
    height: 400,
    animations: {
      enabled: false // Disable animations for better performance
    },
    
  },
  title: {
    text: 'Candlestick and Line Chart',
    align: 'left'
  },
  xaxis: {
    type: 'datetime',
    show: false,
    
    labels: {
       min: currentTimestamp - 1 * 60 * 60 * 1000,
       max: currentTimestamp,
       show: false, // Hide the x-axis line
    },
    
      
    min: sixHoursAgo, // Set the minimum value for the x-axis to 6 hours ago
    max: currentTimestamp,
  },
  yaxis: [
    {
      seriesName: "Candlestick",
      tooltip: {
        enabled: true
      },
      tickAmount: 6 // Adjust the number of ticks on the y-axis for Candlestick and Line charts as needed
    },
    
  ],
  dataLabels: {
    enabled: false // Disable data labels (values on top of the candles and line)
  },
  markers: {
    size: 0 // Hide data point markers on the charts
  },
  candlestick: {
    wicks: {
      enabled: true,
      strokeColors: 'transparent',
      fillColors: 'transparent'
    },
    candlestickWidth: 1 // Set the width of the candlesticks to 2 pixels (you can adjust this value as desired)
  }
};

// Create a new instance for the Combined Chart
var combinedChart = new ApexCharts(document.querySelector("#combinedChart"), combinedOptions);
combinedChart.render();

// Options for the Volume Chart
var volumeOptions = {
  grid: {
    show: true // Hide the background grid lines
  },
  series: [
    {
      name: "Volume",
      type: "bar",
      data: [], // Leave this empty for now, we'll populate it later
      color : "#25316d"
    }
  ],
  chart: {
    height: 150,
    animations: {
      enabled: false // Disable animations for better performance
    },
    toolbar: {
      show: false // Hide toolbar for the volume chart
    }
  },
  xaxis: {
    type: 'datetime',
    labels: {
      datetimeFormatter: {
        year: 'yyyy',
        month: "MMM 'yy",
        day: 'dd MMM',
        hour: 'HH:mm'
      }
    },
    min: sixHoursAgo, // Set the minimum value for the x-axis to 6 hours ago
    max: currentTimestamp
  },
  yaxis: {
    seriesName: "Volume",
    opposite: false, // Move y-axis to the right side for Volume chart
    tickAmount: 3 // Adjust the number of ticks on the y-axis for Volume chart as needed
  },
  dataLabels: {
    enabled: false // Disable data labels (values on top of the bars) for the volume chart
  },
  markers: {
    size: 0 // Hide data point markers on the volume chart
  }
};

// Create a new instance for the Volume Chart
var volumeChart = new ApexCharts(document.querySelector("#volumeChart"), volumeOptions);
volumeChart.render();

//Function to fetch Line chart data

function fetchLineChartData(start, end) {
  $.ajax({
    url: "http://localhost:8098/predictions", // URL for line chart data (predictions)
    method: "GET",
    dataType: "json",
    data: {
      start: start,
      end: end
    },
    success: function (lineData) {
      // Sort the data in ascending order based on the timestamp
      console.log(lineData)
      lineData.sort(function (a, b) {
        return new Date(a.timestamp) - new Date(b.timestamp);
      });

      // Map the fetched data to the format required by ApexCharts for the Line chart series
      var lineSeriesData = lineData.map(function (item) {
        return {
          x: new Date(item.timestamp),
          y: parseFloat(item.preprice)
        };
      });

      // Update the series data for the Line chart
      combinedChart.updateSeries([
        {
          name: "Candlestick",
          data: combinedChart.w.globals.series[0].data // Keep the original Candlestick data
        },
        {
          name: "Line Series",
          data: lineSeriesData // Update with the new Line chart data
        }
      ]);

      // Set the x-axis range to fit the entire Line chart data range
      combinedChart.updateOptions({
        xaxis: {
           min: currentTimestamp - 1 * 60 * 60 * 1000,
           max: currentTimestamp
        }
      });
    },
    error: function (xhr, textStatus, errorThrown) {
      console.error("Failed to fetch line chart data from the API.");
      console.error("Status: " + textStatus);
      console.error("Error: " + errorThrown);
    }
  });
}

var currentTimestamp = new Date().getTime();

// Fetch and update initial data on the Combined chart (last 12 hours)
var twelveHoursAgo = currentTimestamp - 12 * 60 * 60 * 1000;
fetchCombinedChartData(twelveHoursAgo, currentTimestamp);

function fetchCombinedChartData(start, end) {
  // Fetch Candlestick chart data
  $.ajax({
    url: "http://localhost:8098/chart", // URL for historical data for the candlestick chart
    method: "GET",
    dataType: "json",
    data: {
      start: start,
      end: end
    },
    success: function (historicalData) {
      // Sort the data in ascending order based on the timestamp
      historicalData.sort(function (a, b) {
        return new Date(a.timestamp) - new Date(b.timestamp);
      });

      // Map the fetched data to the format required by ApexCharts for the candlestick series
      var candlestickSeriesData = historicalData.map(function (item) {
        return {
          x: new Date(item.timestamp),
          y: [parseFloat(item.m5_start), parseFloat(item.m5_max), parseFloat(item.m5_min), parseFloat(item.m5_end)]
        };
      });

      // Update the series data for the Candlestick and Line charts
      combinedChart.updateSeries([
        {
          name: "Candlestick",
          data: candlestickSeriesData
        },
        {
          name: "Line Series",
          data: [] // Empty data for the Line chart, to be fetched later
        }
      ]);
   
      // Set the x-axis range to fit the entire data range
      combinedChart.updateOptions({
        xaxis: {
           min: currentTimestamp - 1 * 60 * 60 * 1000,
            max: currentTimestamp
        }
      });
      volumeSeriesData = historicalData.map(function (item) {
          return {
            x: new Date(item.timestamp),
            y: parseFloat(item.volume)
          };
        });
      // Fetch and update Line chart data
      fetchLineChartData(start, end)
      
      volumeChart.updateSeries([
          {
            name: "Volume",
            data: volumeSeriesData // For the volume chart
          }
        ]);
    },
    error: function (xhr, textStatus, errorThrown) {
      console.error("Failed to fetch historical data for the Candlestick chart from the API.");
      console.error("Status: " + textStatus);
      console.error("Error: " + errorThrown);
    }
  });
}

function updateCharts() {
    var newStart = end;
    var newEnd = new Date().getTime();
    fetchCombinedChartData(newStart, newEnd);
  }

  // Update the charts every 10 seconds
  setInterval(updateCharts, 10000);

  function handleXAxisRangeUpdate({ min, max }) {
    // Calculate the time difference between max and min in milliseconds
    var timeDiff = max - min;

    // Check if the time difference is greater than or equal to 12 hours
    if (timeDiff >= 12 * 60 * 60 * 1000) {
      // Fetch new data for the updated range
      fetchCombinedChartData(min, max);
    }
  }

  // Add event listener for the zoomed event on the Combined Chart
  combinedChart.on("zoomed", handleXAxisRangeUpdate);