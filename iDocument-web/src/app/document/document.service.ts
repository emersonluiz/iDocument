import { AppConfigService } from './../app-config.service';
import { HttpService } from './../http/http.service';
import { Injectable } from '@angular/core';

@Injectable()
export class DocumentService {

  private url: string;
  
  constructor(private httpService: HttpService,
              private appService: AppConfigService) {
    this.url = this.appService.baseUrl
  }

  create(data: FormData) {
    return this.httpService.post(this.url + "/" + this.appService.documentUrl, data)
            .map(res => this.httpService.extractData(res))
            .catch(error => this.httpService.handleError(error))
  }

  update(data: FormData, documentId: string) {
    return this.httpService.put(this.url + "/" + this.appService.documentUrl + "/" + documentId, data)
            .map(res => this.httpService.extractData(res))
            .catch(error => this.httpService.handleError(error))
  }

  listPerson() {
    return this.httpService.get(this.appService.personUrl)
            .map(res => this.httpService.extractData(res))
            .catch(error => this.httpService.handleError(error))
  }

  listDocument(personId: string) {
    return this.httpService.get(this.url + "/" + this.appService.documentUrl + "/person/" + personId)
            .map(res => this.httpService.extractData(res))
            .catch(error => this.httpService.handleError(error))
  }

  loadDocument(documentId: string) {
    return this.httpService.get(this.url + "/" + this.appService.documentUrl + "/" + documentId)
    .map(res => this.httpService.extractData(res))
    .catch(error => this.httpService.handleError(error))
  }

  delete(id: string) {
    return this.httpService.delete(this.url + "/" + this.appService.documentUrl + '/' + id)
            .map(res => this.httpService.extractData(res))
            .catch(error => this.httpService.handleError(error))
  }

  downloadDocument(id: string) {
    return this.httpService.getFile(this.url + "/" + this.appService.documentUrl + "/" + id + "/file")
            .map(res => res)
            .catch(error => this.httpService.handleError(error))
  }
}
