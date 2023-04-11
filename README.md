# Pabulum

Pabulum is an Android application enabling users to browse recipes from numerous online sources, filtered by diet and meal type. A recipe’s summary displays a clear list of ingredients and additional nutritional information. Recipes can then be saved to a local database for future reference.

Technologies used include:
* Kotlin
* Remote API
* Room Database

## Recipes Fragment 
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
  <img src="https://user-images.githubusercontent.com/42835959/231079785-1c7b103e-bb63-4e5d-8ee9-95f060849427.png" height="142" width="282">
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
  <img src="https://user-images.githubusercontent.com/42835959/231082788-8c8032cc-8721-4f15-87c2-2755a1f5a7e9.png" height="113" width="281">
  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
  <img src="https://user-images.githubusercontent.com/42835959/231082802-940e0fb1-7b0d-494e-b6c4-9415eaccec17.png" height="113" width="281">
</p>
<br>

## Features Activity Fragment
When a recipe is clicked it takes the user to the Features Activity. This activity doesn’t have a bottom navigation component. Instead there is a tab layout, divided into three tabs. User can easily navigate between tabs thanks to ViewPager swipe view, also referred to as horizontal paging. ViewPager is a widget provided by Androidx and it depends on the Material Components library. In the top right corner there is a vault icon. Once clicked, it saves the given recipe to a Recipes Vault, which is a local database. Vault icon turns to green when checked. 

Features Activity is inhabited by three Fragments: Summary Fragment, Ingredients Fragment and Directions Fragment.

<b>Summary Fragment</b>
  
Summary Fragment’s layout is constructed of an Image at the top and recipe overview below it. In the bottom right corner of the Image there are likes and cooking time indicators, mentioned earlier in Recipes Fragment. 

<br>
  <p align="center">
    <img src="https://user-images.githubusercontent.com/42835959/231084799-d26c1aab-391b-4601-9434-8441149ad81f.png" height="512" width="243">
  </p>
<br>

Another set of indicators is placed underneath the recipe’s title. These markers present as follows: ‘Vegan’, ‘Vegetarian’, ‘Dairy Free’, ‘Gluten Free’, ‘Budget’ and ‘Healthy’. Indicators turn to green color based on the response the Remote API gives to a search query. Bottom half of the layout is populated with recipe description text. 

<b>Ingredients Fragment</b>

The layout of Ingredients Fragment is composed of a RecyclerView, which is a type of List View. It is used to display sets of data dynamically. Inside the RecyclerView are Card Views stacked vertically. Each card contains an ingredient thumbnail image, generic name, amount required, consistency and an original name, fetched directly from the source.

<br>
<p align="center">
  <img src="https://user-images.githubusercontent.com/42835959/231085584-ea507eb0-70ab-4577-b837-4b4a848c4e65.png" height="512" width="243">
</p>
<br>

<b>Directions Fragment</b>

Directions Fragment’s layout is a WebView. A WebView is an Android component that allows displaying web pages as part of an activity layout. In the Pabulum software it is used to display a website from which the specific recipe was fetched. It contains ingredients, preparation instructions and sometimes more.

<br>
  <p align="center">
    <img src="https://user-images.githubusercontent.com/42835959/231085947-3d8e2258-59a4-45db-975d-013b6a1acada.png" height="512" width="243">
  </p>
<br>

## Vault Fragment
The layout in Vault Fragment displays a RecyclerView composed of Card Views stacked one on top of another. Card Views are populated with recipes that were added to a local database by pressing Vault Icon in the layout of Features Activity.

When the local database has no records, it triggers views informing the user that the Vault is empty. These views are otherwise hidden. 

<br>
<p align="center">
  <img src="https://user-images.githubusercontent.com/42835959/231086356-63991594-acd6-4a0f-9181-1887746a632f.png" height="512" width="243">
  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
  <img src="https://user-images.githubusercontent.com/42835959/231086371-bddee351-530c-4152-ae51-42e359fabcd4.png" height="512" width="243">
</p>
<br>

Recipes can be easily deleted from the Vault in two ways. First option utilizes a LongClick Listener, that answers when the user presses on an item for about a second.  In the latter case a Contextual Action Bar appears at top of the screen, displaying how many items got selected. A Contextual Action Bar is a temporary action bar that replaces the application’s UI for the duration of a given assignment.

<br>
  <p align="center">
    <img src="https://user-images.githubusercontent.com/42835959/231095913-08150a26-39bd-45a6-b4f3-0a080c45830c.png" height="113" width="281">
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
    <img src="https://user-images.githubusercontent.com/42835959/231095928-fbaf6644-c979-48fd-a723-b5d9fd2597b0.png" height="113" width="281">
  </p>
<br>

A Snackbar message appears at the bottom of a screen informing how many items got deleted. 

When a user wants to remove all items from the Vault, there is no need to select recipes one by one. Instead all recipes can be deleted by clicking a Menu Icon in the top right corner of the screen and pressing ‘Remove All’. This triggers a function in MainViewModel that is responsible for removing all recipes saved to a local database.

## Facts Fragment
Facts Fragment shows a single Card View displaying a Food Fact text, fetched from the API. For the duration of data loading, a progress bar appears at the bottom of the screen. The most recent Food Fact is saved to the database and replaced after refreshing the view. A Safe Call function was created for network error events. When the API is unavailable, a Safe Call function returns the last cached Food Fact and displays it instead.


<br>
<p align="center">
  <img src="https://user-images.githubusercontent.com/42835959/231116500-a15af492-15b8-4569-9f5d-b64302cdb072.png" height="512" width="243">
  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
  <img src="https://user-images.githubusercontent.com/42835959/231116520-35dd964a-4406-4d21-a672-5ce18fbed399.png" height="512" width="243">
</p>
<br>

Food Facts can be shared between other Android applications. To do that, user clicks a Share Icon located in the top right corner of the screen. This triggers a share intent, which is Android’s way of sharing data between apps. A share sheet appears in the bottom half of the screen, showing compatible applications. Food Fact can then be shared with other users in text format.

## Dark Mode
The Pabulum application is configured to adapt to Dark Mode settings. A separate style and a color sheet were created, providing different color configurations. 

<br>
  <p align="center">
    <img src="https://user-images.githubusercontent.com/42835959/231120841-16ee4162-b002-4aa7-b4ae-694047a8973c.png" height="512" width="243">
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
    <img src="https://user-images.githubusercontent.com/42835959/231120857-d233c35e-e758-4b51-9ac9-d11e62b3045e.png" height="512" width="243">
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
    <img src="https://user-images.githubusercontent.com/42835959/231120892-2de082b4-0aff-4b31-b78f-34628087aa73.png" height="512" width="243">
  </p>
<br>

## Remote API
The Remote API allows communication between the Pabulum software and online recipe sources. For this project I chose the Spoonacular Recipe and Food API, which is one of the leading APIs in the cooking sector. It provides thousands of recipes, nutritional information, ingredients visualizations as well as a rich documentation, describing in detail all queries that can be used with their endpoints. Spoonacular offers their registered users both paid and free plans. I used a free variant and the application's requests are limited to 1. 

A unique API Key is generated for each user and it’s used in forming GET requests.
In order to fetch food facts, displayed in Food Facts Fragment, the request presents as follows:

GET https://api.spoonacular.com/food/trivia/random?apiKey=282e3fd202294972****5f0b26d3e6f9

GET request’s response is displayed in JSON format and then imported to Android Studio using Gson library. Gson library is used to convert JSON string to Java Objects as well as the other way around. 

This project depends on Android Data Binding library, a very powerful support library that allows binding data to Views in XML files instead of adding them programmatically. In order to display data from the API, all necessary binding adapters were configured in RecipesRowBinding Class. 

## Room Database
Pabulum application depends on a local database as its first source of truth. It means that the application should always read cached data from the database, before requesting new information.  The reason for this is the performance cost of each individual call. 

This project accommodates four local databases: facts_table, recipes_table, vault_table and an automatically generated room_master_table, used to store metadata information.
Type Converters were implemented in Database Access Methods to convert JSON format API response to a String.



