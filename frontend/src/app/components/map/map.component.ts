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

  readings: Array<Reading> = [];
  comments: Array<Comment> = [];

  constructor(private readingsService: ReadingsService,
              private commentsService: CommentsService) {
  }

  ngOnInit() {
    this.readingsService.getReadings().subscribe(readings => {
      this.readings = readings.filter(reading => {
        return this.location.distance(reading.location) <= 5;
      });
    });

    this.commentsService.getComments().subscribe(comments => {
      this.comments = comments.filter(comment => {
        return this.location.distance(comment.location) <= 5;
      });
    });
  }
}
