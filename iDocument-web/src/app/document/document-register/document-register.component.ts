import { DocumentService } from './../document.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-document-register',
  templateUrl: './document-register.component.html',
  styleUrls: ['./document-register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DocumentRegisterComponent implements OnInit {

  documents = [{id:1, name:'RG'}];
  document = {id:"", typeDocument:{name:""}, issueDate:null, expirationDate:null, validity:"", personId: 1, fileName:""}
  fileExists = false;
  fileBack = false;
  fileToUpload: File = null;

  constructor(private router: Router, private route: ActivatedRoute, private documentService: DocumentService) { }

  ngOnInit() {
    this.fileBack = false;
    this.route.params.subscribe(
      (params: any) => {
        let id = params["id"];
        
        if (id !== undefined && id !== '') {
          this.loadDocument(id);
        }
      }
    );
  }

  loadDocument(documentId: string) {
    this.documentService.loadDocument(documentId).subscribe(
      rtn => {
        this.document = rtn;
        this.document.issueDate = ((this.document.issueDate !== null) ? new Date(this.document.issueDate) : null);
        this.document.expirationDate = ((this.document.expirationDate !== null) ? new Date(this.document.expirationDate) : null);
        if(this.document.fileName !== null && this.document.fileName !== '') {
          this.fileExists = true;
        }
      }
    )
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  onSave() {
    let formData = new FormData();
    if(this.fileToUpload !== null) {
      formData.append('file', this.fileToUpload, this.fileToUpload.name);
    }
    formData.append('data', JSON.stringify(this.document));

    if(this.document.id !== "" && this.document.id !== null && this.document.id !== undefined) {
      this.documentService.update(formData, this.document.id).subscribe(
        rtn => {
          this.onClose();
        }
      )
    } else {
      this.documentService.create(formData).subscribe(
        rtn => {
          this.onClose();
        }
      );
    }
  }

  onDownload(id: string, fileName: string) {
    this.documentService.downloadDocument(id).subscribe(
      rtn => {
        let blob = new Blob([rtn.blob()]);
        let link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = fileName
        
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }
    )
  }

  onClose() {
    this.router.navigate(['']);
  }

  onDeleteFile() {
    this.fileExists = false;
    this.fileBack = true;
  }

  onFileBack() {
    this.fileBack = false;
    this.fileExists = true;
  }

}
