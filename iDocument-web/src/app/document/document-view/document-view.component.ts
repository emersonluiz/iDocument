import { DocumentService } from './../document.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-document-view',
  templateUrl: './document-view.component.html',
  styleUrls: ['./document-view.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DocumentViewComponent implements OnInit {

  document = {id:"", typeDocument:{name:""}, issueDate:null, expirationDate:null, validity:"", personId: 1, fileName:""}

  constructor(private router: Router, private route: ActivatedRoute, private documentService: DocumentService) { }

  ngOnInit() {
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
        console.log(this.document.typeDocument)
      }
    )
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
}
