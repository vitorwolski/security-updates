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

  id:string = "";
  alias:string = "";
  documentTitle:string = "";
  severity:string = "";
  iniRlsDate:string = "";
  curRlsDate:string = "";
  cvrfUrl:string = "";

  secUpds: Array<securityUpdates> = [];
 
  loading: boolean = false;

  resultado: string = "";

  subs: Subscription = new Subscription;

  getFilterId(val:any)
  {
    this.id = val.target.value;
  }

  getFilterAlias(val:any)
  {
    this.alias = val.target.value;
  }

  getFilterDocumentTitle(val:any)
  {
    this.documentTitle = val.target.value;
  }

  getFilterSeverity(val:any)
  {
    this.severity = val.target.value;
  }

  getFilterIniRlsDate(val:any)
  {
    this.iniRlsDate = val.target.value;
  }

  getFilterCurRlsDate(val:any)
  {
    this.curRlsDate = val.target.value;
  }

  getFilterCvrfUrl(val:any)
  {
    this.cvrfUrl = val.target.value;
  }

  public getSecurityUpdates() {
    this.loading = true;
    
    this.subs = this.httpClientService.getSecurityUpdates().subscribe( (response) => {
      this.secUpds = response;
    }, (error) => console.log(error) );

    this.loading = false;
  }
}
