import { DocumentViewComponent } from './document-view/document-view.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DocumentComponent } from './document.component';
import { DocumentRegisterComponent } from './document-register/document-register.component';

const documentRoutes: Routes = [
    { path:'', component: DocumentComponent },
    { path:'register', component: DocumentRegisterComponent },
    { path:'register/:id', component: DocumentRegisterComponent },
    { path:':id', component: DocumentViewComponent },
]

@NgModule({
    imports: [RouterModule.forChild(documentRoutes)],
    exports: [RouterModule]
})
export class DocumentRoutingModule { }
