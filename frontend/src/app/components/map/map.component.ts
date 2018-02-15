import {Component, OnInit} from '@angular/core';
import {ReadingsService} from "../../services/readings/readings.service";
import {Reading} from "../../models/Reading";
import {Comment} from "../../models/Comment";
import {CommentsService} from "../../services/comments/comments.service";
import {Location} from "../../models/Location";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  location = new Location(19.9449799, 50.0646501);

  paths: Array<Path> = [];
  comments: Array<Comment> = [];

  constructor(private readingsService: ReadingsService,
              private commentsService: CommentsService) {
  }

  ngOnInit() {
    this.readingsService.getReadings().subscribe(readings => {
      let filteredReadings = readings.filter(reading => {
        return Location.distance(this.location, reading.location) <= 5;
      });

      let path: Array<Reading> = [];
      let prevReading: Reading;
      for (let i = 0; i < filteredReadings.length; i++) {
        if (!prevReading || this.distance(prevReading, filteredReadings[i])) {
          path.push(filteredReadings[i]);
        } else {
          this.paths.push(new Path(path));
          path = [];
          path.push(filteredReadings[i]);
        }

        prevReading = filteredReadings[i];
      }

      console.log(this.paths);

    });

    this.commentsService.getComments().subscribe(comments => {
      this.comments = comments.filter(comment => {
        return Location.distance(this.location, comment.location) <= 5;
      });
    });
  }

  private distance(reading1: Reading, reading2: Reading): number {
    if (!reading1 || !reading2) {
      return 0;
    }

    return Location.distance(reading1.location, reading2.location);
  }
}

class Path {
  constructor(public readings: Array<Reading>) {
  }
}
