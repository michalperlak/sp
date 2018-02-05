import {Injectable} from '@angular/core';
import {Reading} from '../../models/Reading';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class ReadingsService {

  private readingsApi = 'http://mikepq.nazwa.pl:9090/api/readings';

  constructor(private httpClient: HttpClient) {
  }

  getReadings(): Observable<Array<Reading>> {
    return this.httpClient.get<Array<Reading>>(this.readingsApi);
  }

}
