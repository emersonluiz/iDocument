import { Router } from '@angular/router';
import { DeleteDialogComponent } from './../dialog/delete-dialog/delete-dialog.component';
import { MatDialog } from '@angular/material';
import { DocumentService } from './document.service';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/map';

import * as moment from 'moment'; 

@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DocumentComponent implements OnInit {

  persons = []
  documents = []
  person: any;

  constructor(private documentService: DocumentService,
              private router: Router,
              public dialog: MatDialog) { }
  
  ngOnInit() {
    this.documentService.listPerson().subscribe(
      rtn => {
        this.persons = rtn
      }
    )
  }

  listDocuments() {
    this.documents = [];
    if(this.person !== "" && this.person !== null && this.person != undefined) {
      this.documentService.listDocument(this.person).subscribe(
        rtn => {
          this.documents = rtn
        }
      )
    }
  }

  verifyDate(issueDate, expirationDate, validity) {
    let now =  moment();

    if(expirationDate !== "" && expirationDate !== null) {
      if(moment(moment(new Date(expirationDate))).isSameOrBefore(now.format())) {
        return "danger";
      }

      if(moment(new Date(expirationDate)).isSameOrBefore(now.add(30, 'days').format())) {
        return "warning";
      }
    }

    if(validity !== "" && validity !== null && validity !== undefined) {
      if(issueDate !== "" && issueDate !== null && validity !== undefined) {
        //let total = moment(new Date(issueDate)).add(validity, 'months');

        now =  moment();
        
        let total = moment(new Date(issueDate)).add(12, 'months');
        if(moment(total).isSameOrBefore(now.format())) {
          return "danger";
        }

        if(moment(total).isSameOrBefore(now.add(30, 'days').format())) {
          return "warning";
        }
      } 
    }

    return "";
    
   
  }

  getColor(type) {
    switch (type) {
        case "vencido":
            return "danger";
        case "a vencer":
            return "warning";
        default:
            return "";
    }
}

  onEdit(id: string) {
    this.router.navigate(['register', id]);
  }

  onDetail(id: string) {
    this.router.navigate([id]);
  }

  onAdd() {
    this.router.navigate(['register']);
  }

  onDelete(id: string) {
    let dialogRef = this.dialog.open(DeleteDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if(result === 'true') {
        this.documentService.delete(id).subscribe(
          rtn => {
            this.listDocuments();
          }
        );
      }
    });
  }
}
