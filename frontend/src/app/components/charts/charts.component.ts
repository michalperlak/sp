import {Component, Input, OnInit} from '@angular/core';
import {ReadingsService} from "../../services/readings/readings.service";
import {Reading} from "../../models/Reading";

@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.css']
})
export class ChartsComponent implements OnInit {

  @Input()
  deviceId: string;

  @Input()
  startDate: string;

  @Input()
  endDate: string;

  charts: Array<Array<any>> = [];

  accelerometerX: Array<any>;
  accelerometerY: Array<any>;
  accelerometerZ: Array<any>;

  gyroscopeX: Array<any>;
  gyroscopeY: Array<any>;
  gyroscopeZ: Array<any>;

  timestamps: Array<any> = [];

  readings: Array<Reading> = [];
  filteredReadings: Array<Reading> = [];
  devices: Array<string> = [];

  constructor(private readingsService: ReadingsService) {
  }

  ngOnInit() {
    this.readingsService.getReadings().subscribe(readings => {
      this.readings = readings;
      console.log(readings);
      this.devices = ChartsComponent.removeDuplicates(readings.map(reading => reading.deviceId));
      this.updateCharts();
    });
  }

  updateCharts() {
    let filter = this.getFilter();
    this.filteredReadings = this.readings.filter(filter);
    console.log(this.filteredReadings);

    this.timestamps = this.filteredReadings.map(reading => new Date(reading.timestamp).toISOString());

    let accelerometerX = this.filteredReadings.map(reading => reading.accelerometer[0]);
    this.accelerometerX = [
      {data: accelerometerX, label: 'ax [m/s^2]'}
    ];

    let accelerometerY = this.filteredReadings.map(reading => reading.accelerometer[1]);
    this.accelerometerY = [
      {data: accelerometerY, label: 'ay [m/s^2]'}
    ];

    let accelerometerZ = this.filteredReadings.map(reading => reading.accelerometer[2]);
    this.accelerometerZ = [
      {data: accelerometerZ, label: 'az [m/s^2]'}
    ];

    let gyroscopeX = this.filteredReadings.map(reading => reading.gyroscope[0]);
    this.gyroscopeX = [
      {data: gyroscopeX, label: 'gx [rad/s]'}
    ];

    let gyroscopeY = this.filteredReadings.map(reading => reading.gyroscope[1]);
    this.gyroscopeY = [
      {data: gyroscopeY, label: 'gx [rad/s]'}
    ];

    let gyroscopeZ = this.filteredReadings.map(reading => reading.gyroscope[2]);
    this.gyroscopeZ = [
      {data: gyroscopeZ, label: 'gz [rad/s]'}
    ];

    this.charts = [this.accelerometerX, this.accelerometerY, this.accelerometerZ, this.gyroscopeX, this.gyroscopeY, this.gyroscopeZ];
  }

  getColors(index: number): Array<any> {
    console.log(index);
    console.log(this.colors[index]);
    return this.colors[index];
  }

  private getFilter(): (reading: Reading) => boolean {
    let startDate = ChartsComponent.parseDate(this.startDate);
    let endDate = ChartsComponent.parseDate(this.endDate);

    return (reading: Reading) => {
      let date = new Date(reading.timestamp);
      console.log(date);
      if (startDate && (date.getMilliseconds() < startDate.getMilliseconds())) {
        return false;
      }

      if (endDate && (date.getMilliseconds() > endDate.getMilliseconds())) {
        return false;
      }

      if (this.deviceId) {
        return this.deviceId == reading.deviceId;
      }

      return true;
    };
  }

  private static parseDate(date: string): Date {
    return new Date(date);
  }

  private static removeDuplicates(array: Array<string>): Array<string> {
    return array.sort().filter((item, pos, ary) => {
      return !pos || item != ary[pos - 1];
    });
  }

  colors = [
    [{ // blue
      backgroundColor: "rgba(151,187,205,0.2)",
      borderColor: "rgba(151,187,205,1)",
      pointBackgroundColor: "rgba(151,187,205,1)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgba(151,187,205,0.8)"
    }],
    [{ // green
      backgroundColor: "rgba(70,191,189,0.2)",
      borderColor: "rgba(70,191,189,1)",
      pointBackgroundColor: "rgba(70,191,189,1)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgba(70,191,189,0.8)"
    }],
    [{ // yellow
      backgroundColor: "rgba(253,180,92,0.2)",
      borderColor: "rgba(253,180,92,1)",
      pointBackgroundColor: "rgba(253,180,92,1)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgba(253,180,92,0.8)"
    }],
    [{ // light grey
      backgroundColor: "rgba(220,220,220,0.2)",
      borderColor: "rgba(220,220,220,1)",
      pointBackgroundColor: "rgba(220,220,220,1)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgba(220,220,220,0.8)"
    }],
    [{ // red
      backgroundColor: "rgba(247,70,74,0.2)",
      borderColor: "rgba(247,70,74,1)",
      pointBackgroundColor: "rgba(247,70,74,1)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgba(247,70,74,0.8)"
    }],
    [{ // dark grey
      backgroundColor: "rgba(77,83,96,0.2)",
      borderColor: "rgba(77,83,96,1)",
      pointBackgroundColor: "rgba(77,83,96,1)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgba(77,83,96,1)"
    }]
  ];
}
