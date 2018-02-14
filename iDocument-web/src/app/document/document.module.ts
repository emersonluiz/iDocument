import { DocumentService } from './document.service';
import { MyDateAdapter, MY_DATE_FORMATS } from './../utils/date-util';
import { UserModule } from './../user/user.module';
import { State } from './../utils/state';
import { DialogModule } from './../dialog/dialog.module';
import { FormsModule } from '@angular/forms';
import { MatTooltipModule, MatCardModule, MatIconModule, 
         MatButtonModule, MatSelectModule, MatFormFieldModule, MatChipsModule,
         MatInputModule, MatTabsModule, MatListModule, MatRadioModule,
         MatDatepickerModule, MatNativeDateModule, MatExpansionModule,
         MAT_DATE_LOCALE, MAT_DATE_FORMATS, DateAdapter, MatAutocompleteModule
        } from '@angular/material';

import { FileUploadModule } from 'primeng/primeng';

import { ToolbarModule } from './../toolbar/toolbar.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DocumentComponent } from './document.component';
import { DocumentRoutingModule } from './document-routing.module';
import { DocumentRegisterComponent } from './document-register/document-register.component';
import { DocumentViewComponent } from './document-view/document-view.component';

@NgModule({
  imports: [
    CommonModule,
    DocumentRoutingModule,
    ToolbarModule,
    MatIconModule,
    FormsModule,
    MatCardModule,
    MatTooltipModule,
    DialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTabsModule,
    MatListModule,
    MatRadioModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatExpansionModule,
    MatChipsModule,
    UserModule,
    FileUploadModule
  ],
  declarations: [
    DocumentComponent,
    DocumentRegisterComponent,
    DocumentViewComponent
  ],
  providers: [
    DocumentService
  ]
})
export class DocumentModule { }