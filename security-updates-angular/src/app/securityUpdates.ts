export class securityUpdates {
    id: string = "";
    alias: string = "";
    documentTitle: string = "";
    severity: string = "";
    iniRlsDate: string = "";
    curRlsDate: string = "";
    cvrfUrl: string = "";

    constructor(id:string, alias:string, documentTitle:string, severity:string, iniRlsDate:string, curRlsDate:string, cvrfUrl:string)
    {
        this.id = id;
        this.alias = alias;
        this.documentTitle = documentTitle;
        this.severity = severity;
        this.iniRlsDate = iniRlsDate;
        this.curRlsDate = curRlsDate;
        this.cvrfUrl = cvrfUrl;
    }
}