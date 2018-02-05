import {Injectable} from '@angular/core';
import {Comment} from '../../models/Comment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class CommentsService {

  private commentsApi = 'http://mikepq.nazwa.pl:9090/api/comments';

  constructor(private httpClient: HttpClient) {
  }

  getComments(): Observable<Array<Comment>> {
    return this.httpClient.get<Array<Comment>>(this.commentsApi);
  }

}
