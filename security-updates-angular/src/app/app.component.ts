import { HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { HttpClientService } from './http-client.service';
import { securityUpdates } from './securityUpdates';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'security-updates-angular';

  constructor(private httpClientService: HttpClientService) {
  }

  secUpds: Array<securityUpdates> = [];
 
  loading: boolean = false;
  errorMessage: string = "";

  resultado: string = "";

  subs: Subscription = new Subscription;

  public getSecurityUpdates() {
    this.loading = true;
    this.errorMessage = "";
    
    this.subs = this.httpClientService.getSecurityUpdates().subscribe( (response) => {
      this.secUpds = response;
    }, (error) => console.log(error) );

    this.loading = false;
  }
}
