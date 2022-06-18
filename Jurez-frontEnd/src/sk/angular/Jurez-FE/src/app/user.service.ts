import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {Rezervation} from "./rezervation";
import {User} from "./user";
import {UserDTO} from "./userDTO";
import {BehaviorSubject, catchError, map, Observable, tap, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  token: string | undefined
  private userSubject = new BehaviorSubject<User | undefined>(undefined);
  constructor(private http: HttpClient) {

  }

  getAll(){
    return this.http.get<User[]>('/api/user');
  }

  getById(id:number){
    return this.http.get<User>('/api/user/' + id);
  }

  getUserRezervation(id:number){
    return this.http.get<Rezervation[]>('/api/user/rezervation/' + id).pipe(
      catchError(error => {
        let errorMsg: string;
        if (error.error instanceof ErrorEvent) {
          errorMsg = `Error: ${error.error.message}`;
        } else {
          errorMsg = this.getServerErrorMessage(error);
        }
        throw new Error(errorMsg);
      })
    )
  }

  getByEmail(email: string, token: string | undefined){
    // let headers = new HttpHeaders()
    //   .set('content-type','application/json')
    //   .set('Authorization',`Bearer ${token}`)
    //   .set('Access-Control-Allow-Origin', '*')
    // const headers = new Headers({
    //   'Content-Type': 'application/json',
    //   'Authorization': `Bearer ${token}`
    // })
    return this.http.get<User>('/api/user/email/'+email).pipe(
      catchError(error => {
        let errorMsg: string;
        if (error.error instanceof ErrorEvent) {
          errorMsg = `Error: ${error.error.message}`;
        } else {
          errorMsg = this.getServerErrorMessage(error);
        }
        console.log(errorMsg)
        throw new Error(errorMsg);
      })
    )
  }

  isAutorized(userdto: UserDTO){

    return this.http.post('/api/user/auth/',userdto, {responseType: 'text'})
      .pipe(map(token => {
        if (!token) {
          return undefined
        }
        this.token = token
        return JSON.parse(atob(token.split('.')[1]))
        }))
      .pipe(tap(user => {
        this.getByEmail(user.sub,this.token).subscribe(value => {console.log(value)
        this.userSubject.next(value)});
      }))
      // .pipe(
      // catchError(error => {
      //   let errorMsg: string;
      //   if (error.error instanceof ErrorEvent) {
      //     errorMsg = `Error: ${error.error.message}`;
      //   } else {
      //     errorMsg = this.getServerErrorMessage(error);
      //   }
      //   throw new Error(errorMsg);
      // }))
  }

  get(){
    return this.userSubject.getValue();
  }
  onUserChange(){
    return this.userSubject.asObservable();
}
  logout() {
    this.userSubject.next(undefined);
  }
  getToken(){
    return this.token;
  }
  add(user: User){
      return this.http.post('/api/user', user).pipe(
        catchError(error => {
          let errorMsg: string;
          if (error.error instanceof ErrorEvent) {
            errorMsg = `Error: ${error.error.message}`;
          } else {
            errorMsg = this.getServerErrorMessage(error);
          }
          throw new Error(errorMsg);
        })
      );
  }

  private getServerErrorMessage(error: HttpErrorResponse): string {
    switch (error.status) {
      case 400: {
        return `Not Found: ${error.message}`;
      }
      case 403: {
        return `Access Denied: ${error.message}`;
      }
      case 409: {
        return `Duplicity: ${error.message}`;
      }
      case 500: {
        return `Internal Server Error: ${error.message}`;
      }
      default: {
        return `Unknown Server Error: ${error.message}`;
      }

    }
  }

  // handleError(error: HttpErrorResponse) {
  //   return throwError(error);
  // }

  edit(user: User) {
    return  this.http.put<void>(`/api/user/${user.id}`, user)

  }

  delete(id:number){
    return this.http.delete<User>("/api/user/" + id )
  }
}
