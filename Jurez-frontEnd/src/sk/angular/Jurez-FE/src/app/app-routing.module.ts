import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {LoginComponent} from "./login/login.component";
import {AdminComponent} from "./admin/admin.component";
import {NewUserComponent} from "./new-user/new-user.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {EditUserComponent} from "./edit-user/edit-user.component";
import {MyRezervationComponent} from "./my-rezervation/my-rezervation.component";
import {UserGuard} from "./user.guard";
import {KalendarComponent} from "./kalendar/kalendar.component";

const routes: Routes = [
  {path:'home', component: DashboardComponent , canActivate: [UserGuard]},
  {path:'login', component: LoginComponent},
  {path: 'home/mojerezervacie/:id', component: MyRezervationComponent , canActivate: [UserGuard]},
  {path:'adminpage', component: AdminComponent , canActivate: [UserGuard]},
  {path:'adminpage/newUser', component: NewUserComponent , canActivate: [UserGuard]},
  {path:'adminpage/:id/editUser', component: EditUserComponent , canActivate: [UserGuard]},
  {path:'kalendar',component: KalendarComponent, canActivate: [UserGuard]},
  {path:'', redirectTo: 'login', pathMatch: 'full'},
  {path:'**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
