import { Router } from '@angular/router';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserComponent implements OnInit {

  person: string;
  userName: string;
  personId: string;
  dateLogin: any;

  constructor(private router: Router) {
    
  }

  ngOnInit() {
  }

  onPerson() {
    this.router.navigate(['person', this.personId, 'detail']);
  }
}
