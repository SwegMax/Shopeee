package main

import (
	"net/http"
	"errors"
	"github.com/gin-gonic/gin"
)


type item struct {
	ID        string `json:"id"`
    Heading   string `json:"heading"`
	ItemImage uint32 `json:"itemImage"` //might cause underflow with no negative
    Quantity  uint8  `json:"quantity"` //might need to change to int if app requires.
}

var items = []item{
	{ID: "817546275366", Heading: "JBL Speaker", ItemImage: 0xFFAABBCC, Quantity: 2},
}
//I also want my items to be saved/overwritten in the json file. How to do that?

func getItems(c *gin.Context) {
	c.IndentedJSON(http.StatusOK, items)
}

func itemById(c *gin.Context) {
	id := c.Param("id")
	item, err := getItemById(id)

	if err != nil {
		c.IndentedJSON(http.StatusNotFound, gin.H{"message": "Item not found."})
		return
	}

	c.IndentedJSON(http.StatusOK, item)
}

func checkoutItem(c *gin.Context) {
	id, ok := c.GetQuery("id")

	if !ok {
		c.IndentedJSON(http.StatusBadRequest, gin.H{"message": "Missing id query parameter."})
		return
	}

	item, err := getItemById(id)

	if err != nil {
		c.IndentedJSON(http.StatusNotFound, gin.H{"message": "Item not found."})
		return
	}

	if item.Quantity <= 0 {
		c.IndentedJSON(http.StatusBadRequest, gin.H{"message": "Item not available."})
		return
	}

	item.Quantity -= 1
	c.IndentedJSON(http.StatusOK, item)
}

func returnItem(c *gin.Context) {
	id, ok := c.GetQuery("id")

	if !ok {
		c.IndentedJSON(http.StatusBadRequest, gin.H{"message": "Missing id query parameter."})
		return
	}

	item, err := getItemById(id)

	if err != nil {
		c.IndentedJSON(http.StatusNotFound, gin.H{"message": "Item not found."})
		return
	}

	item.Quantity += 1
	c.IndentedJSON(http.StatusOK, item)
}

func getItemById(id string) (*item, error) {
	for i, b := range items {
		if b.ID == id {
			return &items[i], nil
		}
	}

	return nil, errors.New("item not found")
}

func createItem(c *gin.Context) {
	var newItem item

	if err := c.BindJSON(&newItem); err != nil {
		return
	}

	items = append(items, newItem)
	c.IndentedJSON(http.StatusCreated, newItem)
}

func main() {
	router := gin.Default()
	router.GET("/items", getItems)
	router.GET("/items/:id", itemById)
	router.POST("/items", createItem)
	router.PATCH("/checkout", checkoutItem)
	router.PATCH("/return", returnItem)
	router.Run("localhost:8080")
}


/* curl localhost:8080/items --include --header "Content-Type: application/json" -d @API/database.json --request "POST" */