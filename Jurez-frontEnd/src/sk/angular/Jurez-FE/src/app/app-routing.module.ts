import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {LoginComponent} from "./login/login.component";
import {AdminComponent} from "./admin/admin.component";
import {NewUserComponent} from "./new-user/new-user.component";
import {NotFoundComponent} from "./not-found/not-found.component";

const routes: Routes = [
  {path:'home', component: DashboardComponent},
  {path:'authentification', component: LoginComponent},
  {path:'adminpage', component: AdminComponent},
  {path:'adminpage/newUser', component: NewUserComponent },
  {path:'', redirectTo: 'home', pathMatch: 'full'},
  {path:'**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
