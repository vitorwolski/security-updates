import { Injectable } from '@angular/core';

import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError} from 'rxjs/operators';
import { securityUpdates } from './securityUpdates';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(private http: HttpClient) { }

  getSecurityUpdates(): Observable<any> {
    return this.http.get('http://localhost:64779');

    //return this.http.get('https://jsonplaceholder.typicode.com/albums');

    /*let asd:Observable<securityUpdates>;

    asd = this.http.get('http://localhost:64779');

    alert(asd);

    alert('passei do get');

    return asd;*/
  }
}
