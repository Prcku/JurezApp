import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Rezervation} from "./rezervation";
import {User} from "./user";
import {catchError, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RezervationService {

  constructor(private http: HttpClient) {

  }


  // getById(id:number){
  //   return this.http.get<Rezervation>('/api/rezervation/' + id).pipe(
  //     catchError(error => {
  //       let errorMsg: string;
  //       if (error.error instanceof ErrorEvent) {
  //         errorMsg = `Error: ${error.error.message}`;
  //       } else {
  //         errorMsg = this.getServerErrorMessage(error);
  //       }
  //       throw new Error(errorMsg);
  //     })
  //   );
  // }

  getfreeRezervations(){
    return this.http.get<Rezervation[]>('/api/rezervation/free/').pipe(
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

  getGeneratedRezervation(){
    return this.http.get<Rezervation[][]>('/api/rezervation/kalendar/').pipe(
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

  getRezervationOnThisTime(date: string | null){
    return this.http.get<number>('/api/rezervation/time/' +date).pipe(
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

  bookRezervation(date: string | null, id: number){
    // @ts-ignore
    return this.http.post("/api/rezervation/time/" + id + '/' + date).pipe(
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

  // createRezervation(rezervation: string){
  //   console.log(rezervation);
  //   return this.http.post('/api/rezervation', rezervation).pipe(
  //     catchError(error => {
  //       console.log(error)
  //       let errorMsg: string;
  //       if (error.error instanceof ErrorEvent) {
  //         errorMsg = `Error: ${error.error.message}`;
  //       } else {
  //         errorMsg = this.getServerErrorMessage(error);
  //       }
  //       throw new Error(errorMsg);
  //     })
  //   );
  // }


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

  cancelRezervation(date: Date, id:number){
    // @ts-ignore
    return this.http.delete("/api/rezervation/time/cancel/" + date + '/' + id ).pipe(
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
}
