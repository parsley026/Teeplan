import { initializeApp } from "firebase/app";
import { getAuth, signInWithEmailAndPassword } from "firebase/auth";
import { getDatabase, ref, get, child } from "firebase/database";

const firebaseConfig = {
  apiKey: "AIzaSyCgON90z2wvYH0sdP5aMSiFIXm9yU7WiG0",
  authDomain: "fir-tutorial-e7fdf.firebaseapp.com",
  databaseURL: "https://fir-tutorial-e7fdf-default-rtdb.europe-west1.firebasedatabase.app",
  projectId: "fir-tutorial-e7fdf",
  storageBucket: "fir-tutorial-e7fdf.appspot.com",
  messagingSenderId: "382392829369",
  appId: "1:382392829369:web:22655e3d93f6d284da2268"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

// Initialize Other Features
const auth = getAuth(app);
const database = getDatabase(app);

// Initialize registerUser
// const register = document.getElementById('register');
// register.addEventListener("click", function(event) {
//   event.preventDefault();
  
//   const first_name = "none";
//   const last_name = "none";
//   const is_admin = false;
//   const email = document.getElementById("email_input").value;
//   const password = document.getElementById("password_input").value;

//   const data = {
//     first_name : first_name,
//     last_name : last_name,
//     is_admin : is_admin,
//     email : email,
//     password : password
//   };

//   createUserWithEmailAndPassword(auth, email, password)
//   .then((credentials) => {
//     const ID = auth.currentUser.uid;
//     alert();
//     set(ref(database, 'users/' + ID), data)
//   })
//   .catch((error) => {
//     const errorCode = error.code;
//     const errorMessage = error.message;
//   });
// })

// Initialize loginAdmin
export function login(email, password, callback) {
  signInWithEmailAndPassword(auth, email, password)
    .then((credentials) => {
      get(child(ref(database), 'users/' + credentials.user.uid)).then((snapshot) => {
        const data = snapshot.val();
        if (snapshot.exists() && data.is_admin === true) {
          callback(true,null);
        } else {
          callback(false, "user is not an admin");
        }
      });
    })
    .catch((error) => {
      const errorCode = error.code;
      const errorMessage = error.message;
      
      callback(false,"Incorrect login or password");
      console.error(errorCode + errorMessage);
    });
}

// Initialize getUsers
export async function getUsers() {
  try {
    const snapshot = await get(child(ref(database), 'users'));
    const users = [];
    if (snapshot.exists()) {
      snapshot.forEach((childSnapshot) => {
        users.push(childSnapshot.val());
      });
    }
    return users;
  } catch (error) {
    const errorCode = error.code;
    const errorMessage = error.message;

    console.error("getUsers faild")
    console.error(errorCode + errorMessage);
    return [];
  }
}

// Initialize getCoupons
export async function getCoupons() {
  try {
    const snapshot = await get(child(ref(database), 'coupons'));
    const coupons = [];
    if (snapshot.exists()) {
      snapshot.forEach((childSnapshot) => {
        coupons.push(childSnapshot.val());
      });
    }
    return coupons;
  } catch (error) {
    const errorCode = error.code;
    const errorMessage = error.message;

    console.error("getUsers faild")
    console.error(errorCode + errorMessage);
    return [];
  }
}

// Initialize getEvents
export async function getEvents() {
  try {
    const snapshot = await get(child(ref(database), 'events'));
    const events = [];
    if (snapshot.exists()) {
      snapshot.forEach((childSnapshot) => {
        events.push(childSnapshot.val());
      });
    }
    return events;
  } catch (error) {
    const errorCode = error.code;
    const errorMessage = error.message;

    console.error("getEvents faild")
    console.error(errorCode + errorMessage);
    return [];
  }
}

