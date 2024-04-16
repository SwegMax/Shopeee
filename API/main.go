package main

import (
	"net/http"
	"errors"
	"github.com/gin-gonic/gin"
	"encoding/json"
	"fmt"
	"io/ioutil"
)

type Item struct {
	itemId    string `json:"itemid"`
    Heading   string `json:"heading"`
	ItemImage uint32 `json:"itemImage"` //might cause underflow with no negative
    Quantity  uint8  `json:"quantity"` //might need to change to int if app requires.
}

type User struct {
	userId	int	 'json:userid'
	Items []Item 'json:"items"'
}

var userItems = make(map[int][]Item)

var Items = []Item{
	{itemId: "817546275366", Heading: "JBL Speaker", ItemImage: 0xFFAABBCC, Quantity: 2},
}

//User Item management
func getUsers(c *gin.Context) {
	c.IndentedJSON(http.StatusOK, userItems)
}

func getUserItems(c *gin.Context){
	userId := c.Param("userid")
	items, ok := userItems[userId]
	if !ok {
		c.IndentedJSON(http.StatusNotFound, gin.H{"message": "User not found."})
		return
	}
	c.IndentedJSON(http.StatusOK, items)
}

func checkoutItem(c *gin.Context) {
	userId := c.Query("userid")
	itemId := c.Query("itemid")

	items, ok := userItems[userId]

	if !ok {
		c.IndentedJSON(http.StatusNotFound, gin.H{"message": "User not found."})
		return
	}

	for i, Item := range items {
		if Item.itemId == itemId {
			if items[i].Quantity <= 0 {
				c.IndentedJSON(http.StatusBadRequest, gin.H{"message": "Item not found."})
				return
			}
			items[i].Quantity--
			c.IndentedJSON(http.StatusOK, items[i])
			return
		}
	}

	c.IndentedJSON(http.StatusNotFound, gin.H{"message": "Item not found."})
}

func returnItem(c *gin.Context) {
	userId := c.GetQuery("userid")
	itemId := c.GetQuery("itemid")

	items, ok := userItems[userID]
	if !ok {
		c.IndentedJSON(http.StatusNotFound, gin.H{"message": "User not found"})
		return
	}

	for i, Item := range items {
		if Item.itemId == itemId {
			items[i].Quantity++
			c.IndentedJSON(http.StatusOk, items[i])
			return
		}
	}
	
	c.IndentedJSON(http.StatusNotFound, gin.H{"message": "Item not found."})
}

//TODO: I also want my items to be saved/overwritten in the json file. How to do that?
func loadUserItemsFromJSON() error {
	data, err := ioutil.ReadFile(data.json)
	if err != nil {}
		return err
	}

	var userData []User
	err = json.Unmarshal(data, &userData)
	if err != nil {
		return err
	}

	for _, User := range userData {
		userItems[User.userId] = user.Items
	}

	return nil
}




//Market item management
func itemById(c *gin.Context) {
	itemid := c.Param("itemid")
	Item, err := getItemById(itemid)

	if err != nil {
		c.IndentedJSON(http.StatusNotFound, gin.H{"message": "Item not found."})
		return
	}

	c.IndentedJSON(http.StatusOK, Item)
}

func getItemById(itemid string) (*Item, error) {
	for i, b := range Items {
		if b.itemId == itemid {
			return &Items[i], nil
		}
	}

	return nil, errors.New("Item not found")
}

func createItem(c *gin.Context) {
	var newItem Item

	if err := c.BindJSON(&newItem); err != nil {
		return
	}

	Items = append(Items, newItem)
	c.IndentedJSON(http.StatusCreated, newItem)
}

func main() {
	err := loadUserItemsFromJSON()
	if err != nil {
		fmt.PrintIn("Error loading user items from JSON:", err)
		return
	}

	router := gin.Default()
	router.GET("/users", getUsers)
	router.GET("/users/:userId/items", getUserItems)
	router.GET("/items", getItems)
	router.GET("/items/:itemid", itemById)
	router.POST("/items", createItem)
	router.PATCH("/checkout", checkoutItem)
	router.PATCH("/return", returnItem)
	router.Run("localhost:8080")
}


/* curl localhost:8080/items --include --header "Content-Type: application/json" -d @API/database.json --request "POST" */