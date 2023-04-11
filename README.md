# Pabulum

Pabulum is an Android application enabling users to browse recipes from numerous online sources, filtered by diet and meal type. A recipe’s summary displays a clear list of ingredients and additional nutritional information. Recipes can then be saved to a local database for future reference.

Technologies used include:
* Kotlin
* Remote API
* Room Database

Recipes Fragment acts as a landing page. The layout is composed of Card Views stacked vertically one on top of the other. Card Views are part of the Android’s Material Components Library. 

<br>
<p align="center">
  <img src="https://user-images.githubusercontent.com/42835959/231076712-4fa3217d-d137-440f-a418-c243da2396fa.png">
</p>
<br>

Each Card View displays a recipe’s thumbnail image, title and a short description of the dish. Near the bottom of the Card, four markers indicate the number of likes the given recipe has received, preparation time, as well as whether ingredients are vegetarian and/or gluten-free. This data is fetched from the Remote API dynamically.

Navigation Component is placed at the bottom of the screen and is constructed of three items: Recipes, Vault and Facts which are the applications main Fragments. Current location icon is highlighted. 

<br>
<p align="center">
  <img src="https://user-images.githubusercontent.com/42835959/231079785-1c7b103e-bb63-4e5d-8ee9-95f060849427.png" height="200">
</p>
<br>

Green Floating Action Button in the bottom right corner, opens a bottom sheet dialog (Figure 3.2.1c). Bottom sheet dialog is a fragment itself and it inherits from Android’s Dialog Fragment Class. User is then presented with a choice of various meal types and diets. These preferences are remembered after restarting the app. Values are stored as preference keys in a local data repository and then injected into Recipes Fragment. Default meal type and diet are set in Constants class.

“Search” button fetches new data from the API, by applying two search queries. One for meal type and one for diet. Those new values are then displayed in the new recipes list. 

Menu at the top of the view displays fragments name and a Search Icon. Clicking the icon opens the text field for a user to type in a searched keyword. This text field triggers a function applying an API request on the remote data source and in consequence displaying a list of updated recipes, containing the given keyword in their ingredients list.

<br>
<p align="center">
  <img src="https://user-images.githubusercontent.com/42835959/231079971-f60b746e-87d6-42d4-a5cb-36b5d796d690.png" height="512" width="243">
  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
  <img src="https://user-images.githubusercontent.com/42835959/231079995-68de2ec8-3295-4dc1-ba2d-d2426ae8764a.png" height="512" width="243">
</p>
<br>

For both, Bottom Sheet Dialog and Recipes Search, Safe Call functions were configured in case of network error. Toast message appears in the bottom of the view, indicating if the connection has been lost or when it is back online.

<br>
<p align="center">
  <img src="https://user-images.githubusercontent.com/42835959/231082788-8c8032cc-8721-4f15-87c2-2755a1f5a7e9.png" height="" width="">
  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
  <img src="https://user-images.githubusercontent.com/42835959/231082802-940e0fb1-7b0d-494e-b6c4-9415eaccec17.png" height="" width="">
</p>
<br>

