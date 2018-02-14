import { Injectable } from '@angular/core';

@Injectable()
export class AppConfigService {

  constructor() { }

  private privateZipcodeUrl = "https://viacep.com.br/ws";
  private privateBaseUrl: string = 'http://localhost:9091/iDocument/api';
  private privatePersonUrl: string = 'http://www.mocky.io/v2/5a5e9c47330000b03019244f';
  private privateUserUrl: string = '/usuarios';
  private privateAuthUrl: string = '/login';
  private privateDocumentTypeUrl: string = '/type-documents';
  private privateDocumentUrl: string = 'documents'

  public get baseUrl(): string {
    return this.privateBaseUrl
  }

  public get personUrl(): string {
    return this.privatePersonUrl;
  }

  public get userUrl(): string {
    return this.privateUserUrl;
  }

  public get zipcodeUrl(): string {
    return this.privateZipcodeUrl;
  }

  public get authUrl(): string {
    return this.privateAuthUrl;
  }

  public get documentTypeUrl(): string {
    return this.documentTypeUrl;
  }

  public get documentUrl(): string {
    return this.privateDocumentUrl;
  }
}
